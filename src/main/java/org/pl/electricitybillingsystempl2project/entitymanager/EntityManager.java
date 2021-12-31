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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityManager<T> {

    private final String filename;
    private String rootPath;
    private ObjectMapper mapper = new ObjectMapper();
    private Class<T> entityType;


    public Function<Class, List<Field>> getAllClassFields = cls1 -> {
        Class superclass = cls1.getSuperclass();
        List<Field> fields = new LinkedList<>(Arrays.asList(FieldUtils.getAllFields(superclass)));
        while (superclass != null) {
            fields.addAll(Arrays.asList(FieldUtils.getAllFields(superclass)));
            superclass = superclass.getSuperclass();
        }
        return fields;
    };

    public BiFunction<String, Class, Field> searchForFieldOfClass = (key, cls) ->
            getAllClassFields.apply(cls)
                    .stream().filter(f -> f.getName().equals(key))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("no attribute of name " + key + " was found"));

    public Function<File, Try<List<T>>> readAll
            = (file) -> Try.of(() -> mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, this.entityType)));

    public Function<File, Try<List>> readAllAsHashMap =
            (file) -> Try.of(() -> mapper.readValue(file, new TypeReference<List>() {
            }));

    public EntityManager(Class<T> cls) {
        this.filename = cls.getSimpleName() + ".json";
        this.entityType = cls;
    }


    public Try<T> save(T entity) {
        return Option.of(entity).toTry().flatMap(e -> {
            Path path = getFullPath();
            return FilesManager.getOrCreateFile(path.toString())
                    .toTry()
                    .flatMap(readAll)
                    .map(all -> {
                        all.add(e);
                        return all;
                    }).flatMap(all -> Try.of(() -> FilesManager.writeToFile(all, path.toString())))
                    .map(paths -> e);
        });
    }

    public Path getFullPath() {
        return Path.of(rootPath, this.filename);
    }


    public Try<List<T>> searchByKeyValue(String key, Object value) {
        return readAll.apply(getFullPath().toFile())
                .map(all -> all.stream()
                        .peek(x -> System.out.println(x.getClass()))
                        .filter(isValueFoundPredicate(key, value))
                        .collect(Collectors.toList()));
    }


    private Predicate<T> isValueFoundPredicate(String key, Object value) {
        return obj -> Option.of(searchForFieldOfClass.apply(key, this.entityType))
                .filter(f -> Try.of(() -> f.get(obj) == value).getOrElseGet(throwable -> false))
                .isEmpty();

    }

    public Try<T> update(String key, Object value, T entity) {
        Path path = getFullPath();
        return searchByKeyValue(key, value).flatMap(allMatches -> {
            if (allMatches.size() == 1) {
                return readAll.apply(getFullPath().toFile())
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

}

