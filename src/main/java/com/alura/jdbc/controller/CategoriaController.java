package com.alura.jdbc.controller;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDao;

    //Constructor
    public CategoriaController() {
        
        var conexion = new ConnectionFactory();
        
        this.categoriaDao = new CategoriaDAO(conexion.recuperarConexion());
    }

    public List<Categoria> listar() {
        // TODO
        return categoriaDao.listar();
    }

    public List<Categoria> cargaReporte() {
        // TODO
        return this.listar();
    }

}
