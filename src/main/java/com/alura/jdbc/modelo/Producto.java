package com.alura.jdbc.modelo;

/**
 *
 * @author giova
 */
public class Producto {

    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Integer cantidad;

    //Constructor...
    public Producto(String nombreEntrante, String descripcionEntrante, Integer cantidadEntrante) {
        this.nombre = nombreEntrante;
        this.descripcion = descripcionEntrante;
        this.cantidad = cantidadEntrante;
    }

    //SobrecargaContructor
    public Producto(int idEntrante, String nombreEntrante, String descripcionEntrante, int cantidadEntrante) {
        this.idProducto = idEntrante;
        this.nombre = nombreEntrante;
        this.descripcion = descripcionEntrante;
        this.cantidad = cantidadEntrante;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Object getId() {
        return this.idProducto;
    }

    public void setId(int idEntrante) {
        this.idProducto = idEntrante;
    }

    //Sobrescribiendo el metodo String.format, para especificarle que imprimira...
    @Override
    public String toString() {
        return String.format(
                "{id: %s, nombre: %s, descripcion: %s, cantidad: %d",
                this.idProducto,
                this.nombre,
                this.descripcion,
                this.cantidad);
    }

}
