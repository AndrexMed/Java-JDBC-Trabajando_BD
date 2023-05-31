package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductoController {

    //Cuando se instancie este Objeto, se inicializa la conexion.
    private ProductoDAO productoDao;

    //Constructor Para Inicializar cuando se Inicie el ProductoController
    public ProductoController() {

        this.productoDao = new ProductoDAO(new ConnectionFactory().recuperarConexion());

    }

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

    public int eliminar(Integer idEntrante) {
        return productoDao.eliminar(idEntrante);
    }

    public List<Producto> listar() {  
        return productoDao.listar();
    }

    public void guardar(Producto productoEntrante) {
        productoDao.guardarProducto(productoEntrante);
    }

}
