package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private String nombreDelProducto;

    private double precio;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String descripcion;
    private String imagen;
    private String usuario;

    public Publicacion() {
        // JPA requires a no-arg constructor
    }

    public Publicacion(String titulo, Categoria categoria, String nombreDelProducto, double precio, Estado estado,
            String descripcion, String imagen, String usuario) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.nombreDelProducto = nombreDelProducto;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombreDelProducto() {
        return nombreDelProducto;
    }

    public void setNombreDelProducto(String nombreDelProducto) {
        this.nombreDelProducto = nombreDelProducto;
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