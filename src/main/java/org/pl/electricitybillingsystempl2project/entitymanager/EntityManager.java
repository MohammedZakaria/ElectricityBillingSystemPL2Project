package org.pl.electricitybillingsystempl2project.entitymanager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.pl.electricitybillingsystempl2project.entities.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityManager<T> {

    private final String filename;
    private String rootPath;
    private ObjectMapper mapper = new ObjectMapper();
    private Predicate isValueFound;

    public EntityManager(Class<T> cls) {
        this.filename = cls.getSimpleName() + ".json";
    }


    public Try<T> save(T entity) {
        return Option.of(entity).toTry().flatMap(e -> {
            Path path = getFullPath();
            return FilesManager.getOrCreateFile(path.toString())
                    .toTry()
                    .flatMap(readAll())
                    .map(all -> {
                        all.add(e);
                        return all;
                    }).flatMap(all -> Try.of(() -> FilesManager.writeToFile(all, path.toString())))
                    .map(paths -> e);
        });
    }

    private Path getFullPath() {
        return Path.of(rootPath, this.filename);
    }

    public Function<File, Try<List<T>>> readAll() {
        return (file) -> Try.of(() -> mapper.readValue(file, new TypeReference<List<T>>() {
        }));
    }

    public Function<File, Try<List>> readAllAsHashMap() {
        return (file) -> Try.of(() -> mapper.readValue(file, new TypeReference<List>() {
        }));
    }

    public Try<List<T>> searchByKeyValue(String key, Object value) {
        Function<Object,T> transformMapToEntity =
                t -> Try.of(() -> mapper.readValue(mapper.writeValueAsString(t),
                        new TypeReference<T>() {
                        })).get();

        return readAllAsHashMap().apply(getFullPath().toFile())
                .map(all -> (List<T>) all.stream()
                        .filter(isValueFoundPredicate(key, value))
                        .map(transformMapToEntity)
                        .collect(Collectors.toList()));
    }

    private Predicate isValueFoundPredicate(String key, Object value) {
        return obj ->
                Try.of(() -> {
                            HashMap<String, String> map = (HashMap<String, String>) obj;
                            if (map.containsKey(key))
                                return value.equals(map.get(key));
                            else
                                throw new RuntimeException("no attribute of name " + key + " was found");

                        })
                        .onFailure(Throwable::printStackTrace)
                        .get();
    }

    public Try<T> update(String key, Object value, T entity) {
        Path path = getFullPath();
        return searchByKeyValue(key, value).flatMap(allMatches -> {
            if (allMatches.size() == 1) {
                return readAll().apply(getFullPath().toFile())
                        .map(allData -> {
                            allData.removeAll(allMatches);
                            return allData;
                        })
                        .map(allData -> Try.of(() -> FilesManager.writeToFile(allData, path.toString())).toOption())
                        .map(paths -> entity);
            } else return Try.failure(new RuntimeException("couldn't update entity"))
                    .map(o -> (T) o);
        });
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootPath() {
        return this.rootPath;
    }

    private List<Field> getAllClassFields(Class cls) {
        // for each super class get all fields
        Class superclass = cls.getSuperclass();
        List<Field> fields = new LinkedList<>(Arrays.asList(FieldUtils.getAllFields(superclass)));
        while (superclass != null) {
            fields.addAll(Arrays.asList(FieldUtils.getAllFields(superclass)));
            superclass = superclass.getSuperclass();
        }
        return fields;
    }
}
