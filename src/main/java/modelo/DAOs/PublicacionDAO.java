package modelo.DAOs;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidad.Publicacion;

public class PublicacionDAO {

    private EntityManager em;

    public PublicacionDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public List<Publicacion> obtenerPublicaciones() {
        TypedQuery<Publicacion> query = this.em.createQuery("SELECT p FROM Publicacion p", Publicacion.class);
        return query.getResultList();
    }

    public List<Publicacion> obtenerMisPublicaciones(String usuario) {
        TypedQuery<Publicacion> query = this.em.createQuery("SELECT p FROM Publicacion p WHERE p.usuario = :usuario", Publicacion.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    public Publicacion buscarPorId(int id) {
        return this.em.find(Publicacion.class, id);
    }

    public boolean guardarPublicacion(Publicacion publicacion) {
        try {
            this.em.getTransaction().begin();
            this.em.persist(publicacion);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(">>>> ERROR:PublicacionDAO:guardar " + e);
            if (this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
            return false;
        }
    }

    public boolean actualizar(Publicacion publicacion) {
        try {
            this.em.getTransaction().begin();
            this.em.merge(publicacion);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(">>>> ERROR:PublicacionDAO:actualizar " + e);
            if (this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
            return false;
        }
    }

    public boolean eliminarPublicacion(int id) {
        Publicacion p = this.buscarPorId(id);
        if (p == null) return false;
        try {
            this.em.getTransaction().begin();
            this.em.remove(p);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(">>>> ERROR:PublicacionDAO:eliminar " + e);
            if (this.em.getTransaction().isActive()) this.em.getTransaction().rollback();
            return false;
        }
    }
}
