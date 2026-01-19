package modelo.DAOs;

import java.util.Collections;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidad.Mensaje;


public class MensajeDAO {

	private EntityManager em;

	public MensajeDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }
	
	public boolean registrarMensaje(Mensaje mensaje) {
        try {
            em.getTransaction().begin();
            em.persist(mensaje); // Inserta el objeto en la tabla 'mensajes'
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
	
	public List<Mensaje> listarConversacion(String usuarioA, String usuarioB) {
	    try {
	        // Limpiamos la caché del EM para forzar lectura de la base de datos
	    	
	        
	        String jpql = "SELECT m FROM Mensaje m WHERE " +
	                      "(m.remitente = :userA AND m.destinatario = :userB) OR " +
	                      "(m.remitente = :userB AND m.destinatario = :userA) " +
	                      "ORDER BY m.id ASC";
	 
	        em.clear();
	        TypedQuery<Mensaje> query = em.createQuery(jpql, Mensaje.class);
	        query.setParameter("userA", usuarioA);
	        query.setParameter("userB", usuarioB);
	        
	        return query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
	
}
