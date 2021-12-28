package org.pl.electricitybillingsystempl2project.entitymanager;

public class EntityManagerFactory {
    public static <T> EntityManager<T> getEntityManager(Class<T> cls){
        EntityManager<T> em = new EntityManager<>(cls);
        em.setRootPath(FilesConfigurations.getRootPath());
        return em;
    }
}
