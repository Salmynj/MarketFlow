package modelo.DAOs;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidad.Publicacion;
import modelo.entidad.Estado;

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

    // Obtener publicaciones aplicando filtros opcionales: estado, categoría, rango de precio y búsqueda (q)
    public List<Publicacion> obtenerPublicacionesFiltradas(Estado estado, String categoria, Double precioMin, Double precioMax, String q) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Publicacion p WHERE 1=1");
        if (estado != null) jpql.append(" AND p.estado = :estado");
        if (categoria != null && !categoria.trim().isEmpty()) jpql.append(" AND p.categoria = :categoria");
        if (precioMin != null) jpql.append(" AND p.precio >= :precioMin");
        if (precioMax != null) jpql.append(" AND p.precio <= :precioMax");
        if (q != null && !q.trim().isEmpty()) jpql.append(" AND (LOWER(p.titulo) LIKE :q OR LOWER(p.nombreDelProducto) LIKE :q OR LOWER(p.descripcion) LIKE :q)");

        TypedQuery<Publicacion> query = this.em.createQuery(jpql.toString(), Publicacion.class);
        if (estado != null) query.setParameter("estado", estado);
        if (categoria != null && !categoria.trim().isEmpty()) query.setParameter("categoria", categoria);
        if (precioMin != null) query.setParameter("precioMin", precioMin);
        if (precioMax != null) query.setParameter("precioMax", precioMax);
        if (q != null && !q.trim().isEmpty()) query.setParameter("q", "%" + q.toLowerCase() + "%");

        return query.getResultList();
    }
}