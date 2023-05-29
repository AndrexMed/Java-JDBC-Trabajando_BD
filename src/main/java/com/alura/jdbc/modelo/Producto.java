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
    
    
}
