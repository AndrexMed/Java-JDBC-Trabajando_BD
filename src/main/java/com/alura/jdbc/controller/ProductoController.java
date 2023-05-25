package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

    public void modificar(String nombre, String descripcion, Integer id) {
        // TODO
    }

    public void eliminar(Integer id) {
        // TODO
    }

    public List<?> listar() throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/control-de-stock", "root","");
        
        Statement statement = conexion.createStatement();
        
        boolean resultado = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, PRECIO, CANTIDAD FROM PRODUCTOS");
        
        System.out.println(resultado);
        
        conexion.close();
        return new ArrayList<>();
    }

    public void guardar(Object producto) {
        // TODO
    }

}
