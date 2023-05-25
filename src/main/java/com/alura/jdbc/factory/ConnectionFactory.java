package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author giova
 */
public class ConnectionFactory {

    //Metodo para establecer conexion
    public Connection recuperarConexion() throws SQLException {
        
        // Configurar la conexión a la base de datos
       return  DriverManager.getConnection("jdbc:mysql://localhost:3306/control-de-stock",
                                            "root",
                                               "");

    }
}
