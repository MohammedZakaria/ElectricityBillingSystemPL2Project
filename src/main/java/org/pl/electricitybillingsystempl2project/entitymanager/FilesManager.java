package org.pl.electricitybillingsystempl2project.entitymanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class FilesManager {

    private static final Function1<String, CheckedFunction0<File>> getFile = path -> () -> {
        return getFile(path)
                .getOrElseThrow(() -> new RuntimeException("couldn't open a file " + path));
    };

    private static final Function1<String, CheckedFunction0<File>> createFile = path -> () -> {
        Path file = Files.createFile(Path.of(path));
        String initialData = new ObjectMapper().writeValueAsString(new LinkedList<Integer>());
        Files.writeString(file, initialData, StandardOpenOption.APPEND);
        return file.toFile();
    };

    public static Option<File> getFile(String path) {
        File file = Path.of(path).toFile();
        return file.isFile() && file.exists() ? Option.of(file) : Option.none();
    }

    public static Try<File> createFile(String path) {
        return Try.of(() -> Files.createFile(Path.of(path)).toFile());
    }

    public static Option<File> getOrCreateFile(String path) {
        return Try.of(getFile.apply(path))
                .orElse(() -> Try.of(createFile.apply(path)))
                .toOption();
//                .flatMap(file -> file.isFile() && file.exists() ? Option.of(file) : Option.none());
    }

    public static Try<Path> writeToFile(Object entity, String path) {
        return Try.of(() -> {
            Path p = Files.writeString(Path.of(path),
                    new ObjectMapper().writeValueAsString(entity), StandardOpenOption.WRITE);
            System.out.println(entity);
            return p;
                }
        ).onFailure(Throwable::printStackTrace);
    }

}
