package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author giova
 */
public class PruebaPoolDeConexiones {
    public static void main(String[] args) throws SQLException {
        
        //Simulacion de multiples conexiones a una bd
        
        //Instaciamos la clase que hace la conexion...
        ConnectionFactory connectionfactory = new ConnectionFactory();
        
        //Solicitamos 20 conexiones, pero esta configurado para permitir 10 conexiones...
        for (int i = 0; i < 20; i++) {
            
            Connection conexion = connectionfactory.recuperarConexion();
            
            System.out.println("Abriendo la conexion de numero: " + (i+1));
            
        }
    }
}

/*
El objetivo de este ejemplo es simular múltiples solicitudes de conexión a una base de datos
utilizando un pool de conexiones. El pool de conexiones gestiona un conjunto predefinido de conexiones
y se encarga de reutilizar y administrar eficientemente esas conexiones, evitando la sobrecarga
de crear y cerrar conexiones individuales en cada solicitud.
*/