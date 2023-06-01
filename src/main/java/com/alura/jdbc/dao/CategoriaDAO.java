package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Categoria;
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
            String consulta = "SELECT IDCATEGORIA, NOMBRECAT, IDCATEGORIAFK, NOMBRE, CANTIDAD"
                    + " FROM CATEGORIAS "
                    + "INNER JOIN PRODUCTOS ON CATEGORIAS.IDCATEGORIA = PRODUCTOS.IDCATEGORIAFK";

            System.out.println(consulta);

            final PreparedStatement statement = conexion.prepareStatement(consulta);

            try (statement) {

                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                        Integer idCategoria = resultSet.getInt("IDCATEGORIA");
                        String nombreCat = resultSet.getString("NOMBRECAT");

                        var categoria = resultado
                                .stream()
                                .filter(cat -> cat.getIdCategoria().equals(idCategoria))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(idCategoria, nombreCat);

                                    resultado.add(cat);
                                    return cat;
                                });
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}
