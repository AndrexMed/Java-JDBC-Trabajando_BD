package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
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

        //Establecemos la conexion, Instanciando la clase.
        final Connection conexion = new ConnectionFactory().recuperarConexion();

        try (conexion) {
            conexion.setAutoCommit(false); //Este codigo hace que tomemos el control de toda la transaccion...

            /* Obtenemos los datos del Objeto producto, por medio de sus Getters
            String nombre = productoEntrante.getNombre();
            String descripcion = productoEntrante.getDescripcion();
            int cantidad = productoEntrante.getCantidad(); //Ya no necesitamos la conversion*/

            //Integer cantidadMaxima = 50;

            //Almacenamos el nombre de la tabla para mejor organizacion.
            String nombreTabla = "PRODUCTOS";

            // Construimos la consulta SQL, concatenando...
            //Cambiamos los parametros que van a ser enviados por "?", debido al PreparedStatement...
            String consulta = "INSERT INTO " + nombreTabla + " (nombre, descripcion, cantidad) VALUES (? , ? , ?)";

            /*Se crea un objeto Statement llamado "statement" utilizando el método "createStatement()"
        de la conexión. Un objeto Statement se utiliza para ejecutar instrucciones SQL en la base de datos.*/
            //Cambiamos el createStatement por PreparedStatement, ya que este nos previene se Inyecciones SQL
            final PreparedStatement statement = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                
               // do {
                    //int cantidadParaGuardar = Math.min(cantidad, cantidadMaxima); //Esta funcion me retorna el numero minimo entre AyB...Ej si coloco 60, toma cantidadMaxima que vale 50

                    ejecutarRegistro(statement, productoEntrante, consulta); //Llamado al metodo enviandole parametros...

                    //Despues de ejecutar el metodo si el usuario ingreso 60 en cantidad, aqui almacenamos los 10 sobrantes
                    //cantidad = cantidad - cantidadMaxima; //Suponiendo el ejemplo anterior aqui quedarian 10...
                    
                //} while (cantidad > 0); //Volveria a ejecutarse ya que cantidad es mayor que 0 siendo 10 su valor nuevo...

                conexion.commit();

                System.out.println("Transaccion Exitosa!");
            } catch (Exception e) { //Si sucede un error en la transaccion, La BD Se restablece a su estado original...TODO o NADA!

                conexion.rollback();

                System.out.println("No se pudo completar la Transaccion!");
            }
        }
    }

    //Insertamos este codigo dentro de un Metodo...
    public void ejecutarRegistro(PreparedStatement statement, Producto productoEntrante, String consulta) throws SQLException {

        //Poniendo a prueba las transacciones, para tomar el control de ellas
        /*if (cantidad < 50) {
            throw new RuntimeException("Ocurrio un error!");
        }*/
        
        //Al utilizar Prepared, necesitamos enviarle el parametro a la nueva Consulta de esta forma...
        statement.setString(1, productoEntrante.getNombre());//Parametro consulta Posicion 1, le enviamos "nombre"
        statement.setString(2, productoEntrante.getDescripcion());//Parametro 2, descripcion...
        statement.setInt(3, productoEntrante.getCantidad()); //Parametro 3, Cantidad...

        System.out.println(consulta); //Imprimimos la consulta creada en consola...

        //Ejecutamos la consulta.
        //Con preparedStatement, ya no necesitamos colocar nada dentro de execute...
        statement.execute();

        //Después de ejecutar la consulta con éxito, se llama al método getGeneratedKeys del objeto Statement para obtener un objeto ResultSet que contiene las claves generadas automáticamente por la base de datos.
        final ResultSet resultSet = statement.getGeneratedKeys(); //Este metodo almacena las claves primarias generadas en un metodo "resultSet"

        //Iteración del ResultSet: Se utiliza un bucle while para iterar sobre las filas del ResultSet que contiene las claves generadas. En este caso, se espera que haya solo una clave generada.
        //Dentro del bucle, se imprime en la consola el ID del producto insertado utilizando el método getInt(1) del ResultSet.
        try (resultSet) {
            while (resultSet.next()) {
                System.out.println(String.format("Se inserto el producto con el ID: %d", resultSet.getInt(1)));
                resultSet.getInt(1);
            }
        }
    }

}
