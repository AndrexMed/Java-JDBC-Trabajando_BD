package com.alura.jdbc.modelo;

/**
 *
 * @author giova
 */
public class Categoria {

    private Integer idCategoria;
    private String nombreCat;

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
    
}
