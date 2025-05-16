package org.example.Dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (PersistenceUtil.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("bibliotecaPU");
                }
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
