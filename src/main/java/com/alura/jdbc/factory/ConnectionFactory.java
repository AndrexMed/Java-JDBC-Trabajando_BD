package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author giova
 */
//Esta clase actuará como una fábrica para crear y proporcionar conexiones a la base de datos-
public class ConnectionFactory {
    
    private DataSource datasource; //Se utilizará para almacenar el objeto de origen de datos.
    
    //El constructor se encarga de configurar el pool de conexiones utilizando c3p0
    public ConnectionFactory() {    
        
        //Se crea un nuevo objeto ComboPooledDataSource, que es una implementación de DataSource proporcionada por c3p0.
        var pooledDataSource = new ComboPooledDataSource();
        
        //Establecemos los datos de conexion...
        String urlPoolConexion = "jdbc:sqlserver://localhost:1433;" +
                                 "database=control_de_stock;" +
                                 "loginTimeout=30;" +
                                 "TrustServerCertificate=True;";
        
        pooledDataSource.setJdbcUrl(urlPoolConexion);
        pooledDataSource.setUser("Andrex");
        pooledDataSource.setPassword("andres01");
        
        pooledDataSource.setMaxPoolSize(10);
        
        this.datasource = pooledDataSource; //Almacenamos 
    }

    //Se encarga de devolver una conexión de la base de datos.
    //Utiliza el método getConnection() del objeto dataSource para obtener una conexión del pool de conexiones.
    public Connection recuperarConexion() {
        //La idea de este try es evitar la replicacion del mismo en toda la app...
        try{
        return this.datasource.getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
