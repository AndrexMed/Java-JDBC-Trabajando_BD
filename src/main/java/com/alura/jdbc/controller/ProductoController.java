package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

    public void modificar(String nombre, String descripcion, Integer id) {
        // TODO
    }

    public void eliminar(Integer id) {
        // TODO
    }

    public List<Map<String, String>> listar() throws SQLException {

        // Hacemos una instancia de la clase que contiene el metodo para establecer conexion con la BD.
        Connection conexion= new ConnectionFactory().recuperarConexion();
        
        // Crear la consulta SELECT
        String consulta = "SELECT ID, NOMBRE, DESCRIPCION, PRECIO, CANTIDAD FROM PRODUCTOS";

        // Crear el objeto Statement
        Statement statement = conexion.createStatement();

        // Ejecutar la consulta
        statement.execute(consulta);

        //Obtener los resultados
        ResultSet resultSet = statement.getResultSet();

        /*Creamos una Lista*/
        List<Map<String, String>> resultado = new ArrayList<>();

        while (resultSet.next()) { //Se itera sobre los resultados utilizando el método next() del objeto ResultSet*/
            
            /*En cada iteración, se crea un nuevo mapa (fila) y se agregan pares clave-valor para cada campo obtenido del resultado actual.*/
            Map<String, String> fila = new HashMap<>();
            
            fila.put("ID", String.valueOf(resultSet.getInt("ID")));
            fila.put("NOMBRE", resultSet.getString("NOMBRE"));
            fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
            fila.put("PRECIO", String.valueOf(resultSet.getDouble("PRECIO")));
            fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

            //El mapa fila se agrega a la lista resultado.
            resultado.add(fila);
        }

        //Finalmente, se cierra la conexión a la base de datos y se devuelve la lista resultado.
        conexion.close();
        return resultado;
    }

    public void guardar(Object producto) {
        // TODO
    }

}
