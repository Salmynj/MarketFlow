package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.ConexionBDD;
import modelo.Publicacion;

public class PublicacionDAO {

    public Publicacion guardarPublicacion(Publicacion p) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Publicacion actualizar(Publicacion p) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            em.getTransaction().begin();
            Publicacion merged = em.merge(p);
            em.getTransaction().commit();
            return merged;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            em.getTransaction().begin();
            Publicacion p = em.find(Publicacion.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Publicacion buscarPorId(Long id) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            return em.find(Publicacion.class, id);
        } finally {
            em.close();
        }
    }

    public List<Publicacion> obtenerPublicaciones() {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            TypedQuery<Publicacion> q = em.createQuery("SELECT p FROM Publicacion p", Publicacion.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Publicacion> obtenerMisPublicaciones(String usuario) {
        EntityManager em = ConexionBDD.getEntityManager();
        try {
            TypedQuery<Publicacion> q = em.createQuery("SELECT p FROM Publicacion p WHERE p.usuario = :u", Publicacion.class);
            q.setParameter("u", usuario);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
