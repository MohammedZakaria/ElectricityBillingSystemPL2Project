package org.pl.electricitybillingsystempl2project.entitymanager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityManager<T> {

    private final String filename;
    private String rootPath;
    private ObjectMapper mapper = new ObjectMapper();

    public EntityManager(Class<T> cls){
        this.filename = cls.getName() + ".json";
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
        return (file) -> Try.of(() -> mapper.readValue(file, new TypeReference<List<T>>(){}));
    }

    public Try<List<T>> searchByKeyValue(String key, Object value) {
        return readAll().apply(getFullPath().toFile()).map(all -> all.stream().filter(t ->
                Try.of(() -> value.equals(t.getClass().getField(key).get(t)))
                        .onFailure(Throwable::printStackTrace)
                        .get()).collect(Collectors.toList()));
    }

    public Try<T> update(String key, Object value,T entity){
        Path path = getFullPath();
        return searchByKeyValue(key,value).flatMap(allMatches -> {
            if (allMatches.size() == 1){
                return readAll().apply(getFullPath().toFile())
                        .map(allData -> {
                            allData.removeAll(allMatches);
                            return allData;
                        })
                        .map(allData -> Try.of(() -> FilesManager.writeToFile(allData, path.toString())).toOption())
                        .map(paths -> entity);
            }
            else return Try.failure(new RuntimeException("couldn't update entity"))
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
