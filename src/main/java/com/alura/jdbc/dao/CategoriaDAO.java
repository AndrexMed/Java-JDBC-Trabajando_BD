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

        String consulta = "SELECT IDCATEGORIA, NOMBRECAT FROM CATEGORIAS";

        try {
            final PreparedStatement statement = conexion.prepareCall(consulta);

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()){
                        var categoria = new Categoria(resultSet.getInt("IDCATEGORIA"),
                        resultSet.getString("NOMBRECAT"));
                        
                        resultado.add(categoria);
                    }
                };
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}
