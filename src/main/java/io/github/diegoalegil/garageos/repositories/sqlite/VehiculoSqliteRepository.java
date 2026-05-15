package io.github.diegoalegil.garageos.repositories.sqlite;

import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.repositories.VehiculoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehiculoSqliteRepository extends SQLiteConnectionManager implements VehiculoRepository {

    public VehiculoSqliteRepository() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS vehiculos (" +
                "matricula TEXT PRIMARY KEY," +
                "marca TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "anio INTEGER NOT NULL," +
                "kilometraje INTEGER NOT NULL," +
                "tipo_propulsion TEXT NOT NULL)";
        try (Connection connection = this.getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creando tabla vehiculos " + e);
        }
    }

    @Override
    public void guardar(Vehiculo vehiculo) {
        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(
                        "INSERT INTO vehiculos (matricula, marca, modelo, anio, kilometraje, tipo_propulsion) VALUES (?,?,?,?,?,?)")) {

            sentencia.setString(1, vehiculo.getMatricula());
            sentencia.setString(2, vehiculo.getMarca());
            sentencia.setString(3, vehiculo.getModelo());
            sentencia.setInt(4, vehiculo.getAnio());
            sentencia.setInt(5, vehiculo.getKilometraje());
            sentencia.setString(6, vehiculo.getTipoPropulsion().name());

            sentencia.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error guardando vehículo " + e);
        }
    }

    @Override
    public List<Vehiculo> obtenerTodos() {

        List<Vehiculo> vehiculos = new ArrayList<>();

        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection.prepareStatement("SELECT * FROM vehiculos")) {

            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                vehiculos.add(map(resultado));
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo todos los vehiculos" + e);
        }
        return vehiculos;
    }

    @Override
    public Optional<Vehiculo> buscarPorMatricula(String matricula) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(String matricula) {

        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection.prepareStatement("DELETE FROM vehiculos WHERE matricula = ?")) {

            sentencia.setString(1, matricula);

            return sentencia.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error eliminando por matricula" + e);
            return false;
        }
    }

    private Vehiculo map(ResultSet resultado) throws SQLException {
        String matricula = resultado.getString("matricula");
        String marca = resultado.getString("marca");
        String modelo = resultado.getString("modelo");
        int anio = resultado.getInt("anio");
        int kilometraje = resultado.getInt("kilometraje");
        TipoPropulsion tipoPropulsion = TipoPropulsion.valueOf(resultado.getString("tipo_propulsion"));
        return new Vehiculo(matricula, marca, modelo, anio, kilometraje, tipoPropulsion);
    }
}