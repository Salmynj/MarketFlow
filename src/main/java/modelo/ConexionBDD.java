package modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Centraliza la conexión a la base de datos (JPA EntityManagerFactory).
 * Ajusta la unidad de persistencia en resources/META-INF/persistence.xml
 */
public class ConexionBDD {

    // Use lazy initialization to avoid ExceptionInInitializerError on class loading
    private static volatile EntityManagerFactory emf;

    private static EntityManagerFactory crearEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("MarketFlowPU");
        } catch (Throwable ex) {
            // Print full stack trace to stdout/stderr so Tomcat logs capture it
            System.err.println("Error creating EntityManagerFactory for MarketFlowPU:");
            ex.printStackTrace(System.err);
            // Rethrow as unchecked to preserve the original cause for visibility
            throw new RuntimeException("Failed to create EntityManagerFactory: " + ex.getMessage(), ex);
        }
    }

    private static EntityManagerFactory getEmf() {
        if (emf == null) {
            synchronized (ConexionBDD.class) {
                if (emf == null) {
                    emf = crearEntityManagerFactory();
                }
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEmf().createEntityManager();
    }

    public static void cerrar() {
        if (emf != null && emf.isOpen()) emf.close();
    }

    /**
     * Comprueba de forma rápida si la base de datos está accesible.
     * Ejecuta una consulta nativa muy ligera (SELECT 1).
     *
     * @return true si la consulta se ejecutó correctamente y devolvió resultado; false en caso de error.
     */
    public static boolean estaConectado() {
        EntityManager em = null;
        try {
            // Si el EntityManagerFactory falla en crearse, esto lanzará y caerá al catch
            em = getEmf().createEntityManager();
            Object result = em.createNativeQuery("SELECT 1").getSingleResult();
            return result != null;
        } catch (Throwable ex) {
            // Registrar el error (se verá en los logs de Tomcat)
            System.err.println("DB connectivity test failed: " + ex.getMessage());
            return false;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}