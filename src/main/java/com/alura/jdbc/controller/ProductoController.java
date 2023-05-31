package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;
import java.util.List;

public class ProductoController {

    //Cuando se instancie este Objeto, se inicializa la conexion.
    private ProductoDAO productoDao;

    //Constructor Para Inicializar cuando se Inicie el ProductoController
    public ProductoController() {

        this.productoDao = new ProductoDAO(new ConnectionFactory().recuperarConexion());

    }

    public int modificar(String nombreEntrante, String descripcionEntrante, Integer cantidadEntrante, Integer idEntrante) {  
        return productoDao.modificar(nombreEntrante, descripcionEntrante, cantidadEntrante, idEntrante);
    }

    public int eliminar(Integer idEntrante) {
        return productoDao.eliminar(idEntrante);
    }

    public List<Producto> listar() {  
        return productoDao.listar();
    }

    public void guardar(Producto productoEntrante) {
        productoDao.guardarProducto(productoEntrante);
    }

}
