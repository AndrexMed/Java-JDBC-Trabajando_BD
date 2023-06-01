package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giova
 */
public class CategoriaDAO {

    private Connection conexion;

    //Constructor...
    public CategoriaDAO(Connection conexionEntrante) {
        this.conexion = conexionEntrante;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            String consulta = "SELECT IDCATEGORIA, NOMBRECAT FROM CATEGORIAS";

            System.out.println(consulta);

            final PreparedStatement statement = conexion.prepareStatement(consulta);

            try (statement) {

                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Categoria(
                                resultSet.getInt("IDCATEGORIA"),
                                resultSet.getString("NOMBRECAT")));
                    }
                };
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public List<Categoria> listarConProductos() {

        List<Categoria> resultado = new ArrayList<>();

        try {
            // Aquí se define la consulta SQL para obtener los datos de las categorías y los productos.
            String consulta = "SELECT C.IDCATEGORIA, C.NOMBRECAT, P.IDCATEGORIAFK, P.ID, P.NOMBRE, P.CANTIDAD "
                    + "FROM CATEGORIAS C "
                    + "INNER JOIN PRODUCTOS P ON C.IDCATEGORIA = P.IDCATEGORIAFK";

            System.out.println(consulta);

            final PreparedStatement statement = conexion.prepareStatement(consulta);

            try (statement) {

                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                         // Se extraen los valores de las columnas del resultado de la consulta.
                        Integer idCategoria = resultSet.getInt("IDCATEGORIA");
                        String nombreCat = resultSet.getString("NOMBRECAT");

                        var categoria = resultado
                                .stream()
                                .filter(cat -> cat.getIdCategoria().equals(idCategoria))
                                .findAny().orElseGet(() -> {
                                     // Si la categoría no existe en la lista resultado, se crea una nueva categoría y se agrega a la lista.
                                    Categoria cat = new Categoria(idCategoria, nombreCat);

                                    resultado.add(cat);
                                    return cat;
                                });
                        
                        Producto producto = new Producto(resultSet.getInt("ID"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getInt("CANTIDAD"));   
                        
                        categoria.agregar(producto);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}
