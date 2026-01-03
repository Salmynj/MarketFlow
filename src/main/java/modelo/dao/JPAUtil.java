package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.ConexionBDD;

public class JPAUtil {

    public static EntityManager getEntityManager() {
        return ConexionBDD.getEntityManager();
    }

    public static void close() {
        ConexionBDD.close();
    }
}