package org.pl.electricitybillingsystempl2project.entitymanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesConfigurations {

    public static void initiate() throws IOException {
        Path path = Path.of(getRootPath());
        if (!path.toFile().exists())
            Files.createDirectory(path);
    }

    public static String getRootPath(){
        return Paths.get("data").toAbsolutePath().toString();
    }

    public static void register(Class ...classes) throws IOException {
        for (Class cls:classes){
            String path = Path.of(FilesConfigurations.getRootPath(),cls.getSimpleName()+".json").toString();
            FilesManager.getOrCreateFile(path).
                    getOrElseThrow(() -> new IOException("couldn't create file for " + cls.getSimpleName()));
            System.out.println("registering file "+ path);
        }
    }
}
