package org.pl.electricitybillingsystempl2project.entitymanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class FilesManager {

    public static Option<File> getFile(String path){
        File file = Path.of(path).toFile();
        return file.isFile() && file.exists() ? Option.of(file) : Option.none();
    }

    public static Try<File> createFile(String path){
        return Try.of(()-> Files.createFile(Path.of(path)).toFile());
    }

    public static Option<File> getOrCreateFile(String path){
        return Try.of(() -> {
                    Path file = Files.createFile(Path.of(path));
                    String initialData = new ObjectMapper().writeValueAsString(new LinkedList<Integer>());
                    Files.writeString(file,initialData,StandardOpenOption.APPEND);
                    return file;
                })
                .map(Path::toFile)
                .toOption()
                .flatMap(file -> file.isFile() && file.exists() ? Option.of(file) :Option.none());
    }

    public static Try<Path> writeToFile(Object entity, String path) {
        return Try.of(() ->
                Files.writeString(Path.of(path),
                        new ObjectMapper().writeValueAsString(entity), StandardOpenOption.APPEND)
        );
    }

}
