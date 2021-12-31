package org.pl.electricitybillingsystempl2project.entitymanager;

import org.junit.jupiter.api.Test;
import org.pl.electricitybillingsystempl2project.entities.Admin;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerTest {

    @Test
    void searchForFieldOfClass(){
        assertDoesNotThrow(()->EntityManagerFactory.getEntityManager(Admin.class)
                .searchForFieldOfClass.apply("email",Admin.class).getName());
        assertEquals("email",EntityManagerFactory.getEntityManager(Admin.class)
                .searchForFieldOfClass.apply("email",Admin.class).getName());
    }

    @Test
    void findClassField(){
        assertDoesNotThrow(()->EntityManagerFactory.getEntityManager(Admin.class)
                .getAllClassFields.apply(Admin.class));
        assertTrue(EntityManagerFactory.getEntityManager(Admin.class)
                .getAllClassFields.apply(Admin.class).stream()
                .peek(field -> System.out.println(field.getName()))
                .anyMatch(f -> "email".equals(f.getName())));

    }

    @Test
    void readAll(){
        EntityManager<Admin> em = EntityManagerFactory.getEntityManager(Admin.class);
        assertDoesNotThrow(()-> em.readAll.apply(em.getFullPath().toFile()).get());
        assertTrue(em.readAll.apply(em.getFullPath().toFile()).get()
                .stream()
                .peek(admin -> System.out.println(admin.getName()))
                .anyMatch(admin -> "admin".equals(admin.getName())));

    }

    @Test
    void searchByKeyValue() {
    }
}