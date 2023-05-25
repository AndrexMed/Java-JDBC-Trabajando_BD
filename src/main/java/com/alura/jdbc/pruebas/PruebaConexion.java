package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        
        Connection conexion= new ConnectionFactory().recuperarConexion();

        System.out.println("Cerrando la conexión");

        conexion.close();
    }

}
