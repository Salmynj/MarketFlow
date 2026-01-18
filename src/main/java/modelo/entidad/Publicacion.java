package modelo.entidad;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;



@Entity
@Table(name = "publicaciones")
@NamedQueries({
    @NamedQuery(name = "Publicacion.findAll", query = "SELECT p FROM Publicacion p"),
    @NamedQuery(name = "Publicacion.findByUsuario", query = "SELECT p FROM Publicacion p WHERE p.usuario = :usuario")
})
@XmlRootElement(name="publicaciones")

public class Publicacion implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "nombre_del_producto")
    private String NombreDelProducto;

    @Column(name = "precio")
    private double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "usuario")
    private String usuario;

    // Constructor sin-args requerido por JPA
    public Publicacion() {
    }

    public Publicacion(String titulo, String categoria, String nombreDelProducto, double precio, Estado estado,
            String descripcion, String imagen, String usuario, int id) {
        super();
        this.titulo = titulo;
        this.categoria = categoria;
        this.NombreDelProducto = nombreDelProducto;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.usuario = usuario;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreDelProducto() {
        return NombreDelProducto;
    }

    public void setNombreDelProducto(String nombreDelProducto) {
        this.NombreDelProducto = nombreDelProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
