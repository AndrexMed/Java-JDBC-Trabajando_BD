package com.alura.jdbc.persistencia;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author giova
 */
public class PersistenciaProducto {
    
    private Connection conexion;
    
    //Constructor
    public PersistenciaProducto(Connection conexion){
        this.conexion = conexion;
    }
    
    public void guardarProducto(Producto productoEntrante) throws SQLException{
        
        //Establecemos la conexion, Instanciando la clase.
        final Connection conexion = new ConnectionFactory().recuperarConexion();

        try (conexion) {
            
            //conexion.setAutoCommit(false); //Este codigo hace que tomemos el control de toda la transaccion...

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
                //conexion.commit();

                System.out.println("Transaccion Exitosa!");
            } catch (Exception e) { //Si sucede un error en la transaccion, La BD Se restablece a su estado original...TODO o NADA!

                conexion.rollback();

                System.out.println("No se pudo completar la Transaccion!");
            }
        }
    }
    
    //
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
                productoEntrante.setId(resultSet.getInt(1));
                System.out.println(String.format("Se inserto el producto: %s", productoEntrante)); //Esto solo imprimiria la referencia en memoria, por eso sobreescribiremos el metodo toString();
            }
        }
    }
    
}
