package org.pl.electricitybillingsystempl2project.entitymanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.electricitybillingsystempl2project.entitymanager.FilesManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FilesManagerTest {

    class Customer{
        String name;
        String id;
        public Customer(String name,String id){
            this.name = name;
            this.id = id;
        }
    }

    @BeforeEach
    void setUp() {
//        EntityManager<Customer> em = new EntityManager<>(Customer.class);
//        em.setRootPath("/d/EBS-PL");
//        em.save(new Customer("c1","14324"))
//                .onSuccess(customer -> System.out.println("tell the user that customer is saved"))
//                .onFailure(throwable -> System.out.println("tell the user that customer couldn't be saved"))
//                .get();
    }

    @Test
    void getFile() {
    }

    @Test
    void createFile() {
    }

    @Test
    void getOrCreateFile() {
        FilesManager.getOrCreateFile("/d/Mosup/Java/mosup17/Documents/IntelliJ Projects/ElectricityBillingSystemPL2Project/data/Admin.json")
                .map(file -> Path.of(file.getPath()))
                .map(path -> {
                    Try.of(() -> Files.readString(path))
                            .onFailure(throwable -> fail())
                            .flatMap(json -> Try.of(() -> new ObjectMapper().readValue(json,LinkedList.class)))
                            .onSuccess(linkedList -> assertTrue(linkedList.isEmpty()))
                            .onFailure(throwable -> fail())
                            .get()
                    ;
                    System.out.println(new LinkedList<Integer>(){}.getClass());
                    return path;
                })
                .flatMap(path -> Try.of(()-> Files.deleteIfExists(path)).toOption())
                .getOrElse(Assertions::fail);
    }

    @Test
    void writeToFile() {
        System.out.println(Paths.get("data").toAbsolutePath());
    }
}