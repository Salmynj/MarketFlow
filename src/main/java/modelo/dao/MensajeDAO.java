package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.ConexionBDD;
import modelo.Mensaje;
import java.util.List;

public class MensajeDAO {

    public Mensaje registrarMensaje(Mensaje m) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            return m;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
 
    
    public List<Mensaje> listarConversacion(String usuarioA, String usuarioB) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            TypedQuery<Mensaje> q = em.createQuery(
                    "SELECT m FROM Mensaje m WHERE (m.remitente = :a AND m.destinatario = :b) OR (m.remitente = :b AND m.destinatario = :a) ORDER BY m.id",
                    Mensaje.class); 
            q.setParameter("a", usuarioA);
            q.setParameter("b", usuarioB);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}