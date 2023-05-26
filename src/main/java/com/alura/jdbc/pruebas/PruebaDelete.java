package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author giova
 */
public class PruebaDelete {
    
    public static void main(String[] args) throws SQLException {
        
        //Creamos la conexion instanciando la clase...
        Connection conexion = new ConnectionFactory().recuperarConexion();
        
        //Creamos el objeto statement vinculandolo con la conexion...
        Statement statement = conexion.createStatement();
        
        //Consulta sql
        String consulta = "DELETE FROM PRODUCTOS WHERE ID = 99";
        
        //Ejecutamos la consulta...
        statement.execute(consulta);
        
        System.out.println("Filas Eliminadas: "+statement.getUpdateCount());
    }
    
}
