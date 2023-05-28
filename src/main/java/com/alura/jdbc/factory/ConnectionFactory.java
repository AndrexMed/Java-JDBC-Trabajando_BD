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
        
        // Configurar la conexión a la base de datos con MYSQL
       /*return  DriverManager.getConnection("jdbc:mysql://localhost:3306/control-de-stock",
                                            "root",
                                               "");*/
       
        // Configurar la conexión a la base de datos con SQL SERVER
       return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                + "database=control_de_stock;"
                + "user=Andrex;"
                + "password=andres01;"
                + "loginTimeout=30;" //Este parametro establece el tiempo maximo de espera de conexion...
                + "TrustServerCertificate=True;"); /*Este ultimo parametro se añadio ya que la BD no tiene un certificado
                                                     firmado, ya que el servidor esta con fines educativos...*/
       
    }
}
