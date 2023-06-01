package com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giova
 */
public class Categoria {

    private Integer idCategoria;
    private String nombreCat;
    private List<Producto> productos;

    public Categoria(int idEntrante, String nombreEntrante) {
        this.idCategoria = idEntrante;
        this.nombreCat = nombreEntrante;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }
    

    //Esta sobrescritura se hizo para mostrar los nombres de las categorias en la app...
    @Override
    public String toString() {
        return this.nombreCat;
    }

    public void agregar(Producto producto) {
        if(this.productos == null){
            this.productos = new ArrayList<>();
        }
        
        this.productos.add(producto);
    }
    
    public List<Producto> getProductos(){
        return this.productos;
    }
    
}
