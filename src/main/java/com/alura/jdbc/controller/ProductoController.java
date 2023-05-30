package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer idEntrante) throws SQLException {
        // TODO

        //Creamos la conexion...
        final Connection conexion = new ConnectionFactory().recuperarConexion();

        try (conexion) {
            //Creamos la consulta SQL Para Actualizar datos...
            String consultaForma1 = "UPDATE PRODUCTOS SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = ?";
            String consultaForma2 = "UPDATE PRODUCTOS SET "
                    + "nombre = ?,"
                    + "descripcion = ?,"
                    + "cantidad = ?"
                    + " WHERE id = ?";

            //Creamos un objeto de tipo PreparedStatement llamado statement utilizando una conexión establecida previamente (conexion).
            final PreparedStatement statement = conexion.prepareStatement(consultaForma1);

            try (statement) {
                /*Este código se utiliza para asignar valores a los parámetros de un PreparedStatement en Java,
        lo que permitirá ejecutar consultas SQL parametrizadas con los valores especificados.*/
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, idEntrante);

                statement.execute();

                int updateCount = statement.getUpdateCount();

                System.out.println("Se actualizo un dato con el id: " + idEntrante);

                return updateCount;
            }
        }
    }

    public int eliminar(Integer idEntrante) throws SQLException {
        // TODO

        //Creamos la conexion instanciando la clase...
        final Connection conexion = new ConnectionFactory().recuperarConexion();

        //Consulta sql
        String consulta = "DELETE FROM PRODUCTOS WHERE ID = ?";

        try (conexion) {
            final PreparedStatement statement = conexion.prepareStatement(consulta);

            try (statement) {
                //Al utilizar Prepared, necesitamos enviarle el parametro a la nueva Consulta de esta forma...
                statement.setInt(1, idEntrante);

                //Ejecutamos la consulta...
                statement.execute();

                System.out.println("Se elimino el producto: " + idEntrante);

                return statement.getUpdateCount(); //Retorna un INT con el num de filas modificadas...
            }
        }
    }

    public List<Map<String, String>> listar() throws SQLException {

        // Hacemos una instancia de la clase que contiene el metodo para establecer conexion con la BD.
        final Connection conexion = new ConnectionFactory().recuperarConexion();

        // Crear la consulta SELECT
        String consulta = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTOS";

        try (conexion) {
            // Crear el objeto Statement
            final PreparedStatement statement = conexion.prepareStatement(consulta);

            try (statement) {
                // Ejecutar la consulta
                statement.execute();

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
                    fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

                    //El mapa fila se agrega a la lista resultado.
                    resultado.add(fila);
                }
                return resultado;
            }
        }
    }

    public void guardar(Producto productoEntrante) throws SQLException {
        
        ProductoDAO productoDao = new ProductoDAO(new ConnectionFactory().recuperarConexion());
        
        productoDao.guardarProducto(productoEntrante);
    }



}
