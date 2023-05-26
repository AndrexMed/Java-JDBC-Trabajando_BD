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

    public int eliminar(Integer idEntrante) throws SQLException {
        // TODO
        
        //Creamos la conexion instanciando la clase...
        Connection conexion = new ConnectionFactory().recuperarConexion();
        
        Statement statement = conexion.createStatement();
        
        //Consulta sql
        String consulta = "DELETE FROM PRODUCTOS WHERE ID = "+idEntrante;
        
        //Ejecutamos la consulta...
        statement.execute(consulta);
        
        return statement.getUpdateCount(); //Retorna un INT con el num de filas modificadas...
    }

    public List<Map<String, String>> listar() throws SQLException {

        // Hacemos una instancia de la clase que contiene el metodo para establecer conexion con la BD.
        Connection conexion = new ConnectionFactory().recuperarConexion();

        // Crear la consulta SELECT
        String consulta = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTOS";

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
            fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

            //El mapa fila se agrega a la lista resultado.
            resultado.add(fila);
        }

        //Finalmente, se cierra la conexión a la base de datos y se devuelve la lista resultado.
        conexion.close();
        return resultado;
    }

    public void guardar(Map<String, String> producto) throws SQLException {

        //Establecemos la conexion, Instanciando la clase.
        Connection conexion = new ConnectionFactory().recuperarConexion();

        /*Se crea un objeto Statement llamado "statement" utilizando el método "createStatement()"
        de la conexión. Un objeto Statement se utiliza para ejecutar instrucciones SQL en la base de datos.*/
        Statement statement = conexion.createStatement();

        // Obtenemos los datos del Map Entrante, en variables...
        String nombre = producto.get("NOMBRE");
        String descripcion = producto.get("DESCRIPCION");
        String cantidad = producto.get("CANTIDAD");
        
        //Almacenamos el nombre de la tabla para mejor organizacion.
        String nombreTabla = "PRODUCTOS";

        // Construimos la consulta SQL, concatenando...
        String consulta = "INSERT INTO " + nombreTabla + " (nombre, descripcion, cantidad) VALUES ('" + nombre + "', '" + descripcion + "', '" + cantidad + "')";

        //Ejecutamos la consulta.
        
        statement.execute(consulta,Statement.RETURN_GENERATED_KEYS);
        
        //Después de ejecutar la consulta con éxito, se llama al método getGeneratedKeys del objeto Statement para obtener un objeto ResultSet que contiene las claves generadas automáticamente por la base de datos.
        
        ResultSet resultSet = statement.getGeneratedKeys(); //Este metodo almacena las claves primarias generadas en un metodo "resultSet"
    
    
        //Iteración del ResultSet: Se utiliza un bucle while para iterar sobre las filas del ResultSet que contiene las claves generadas. En este caso, se espera que haya solo una clave generada.
        //Dentro del bucle, se imprime en la consola el ID del producto insertado utilizando el método getInt(1) del ResultSet.
        while (resultSet.next()) {  
            System.out.println(String.format("Se inserto el producto con el ID: %d", resultSet.getInt(1)));
            resultSet.getInt(1);
        }
    }

}
