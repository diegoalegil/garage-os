package io.github.diegoalegil.garageos.repositories.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Mantenimiento;
import io.github.diegoalegil.garageos.repositories.MantenimientoRepository;

public class MantenimientoSqliteRepository extends SQLiteConnectionManager implements MantenimientoRepository {

    public MantenimientoSqliteRepository() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS mantenimientos( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matricula TEXT NOT NULL," +
                "fecha_revision TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "coste REAL NOT NULL," +
                "km_en_la_revision INTEGER NOT NULL," +
                "FOREIGN KEY (matricula) REFERENCES vehiculos(matricula))";

        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(sql)) {
            sentencia.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error creando tabla mantenimientos " + e);
        }
    }

    @Override
    public void guardar(Mantenimiento mantenimiento) {

        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(
                        "INSERT INTO mantenimientos (matricula, fecha_revision, descripcion, coste, km_en_la_revision) VALUES (?,?,?,?,?)")) {

            sentencia.setString(1, mantenimiento.getMatricula());
            sentencia.setString(2, mantenimiento.getFechaRevision().toString());
            sentencia.setString(3, mantenimiento.getDescripcion());
            sentencia.setDouble(4, mantenimiento.getCoste());
            sentencia.setInt(5, mantenimiento.getKmEnLaRevision());

            sentencia.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error guardando el mantenimiento" + e);
        }
    }

    @Override
    public List<Mantenimiento> obtenerPorMatricula(String matricula) {

        List<Mantenimiento> mantenimientosPorMatricula = new ArrayList<>();

        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection
                        .prepareStatement("SELECT * FROM mantenimientos WHERE matricula = ?")) {

            sentencia.setString(1, matricula);

            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                mantenimientosPorMatricula.add(mapRow(resultado));
            }

        } catch (Exception e) {
            System.err.println("Error listando mantenimientos por matricula" + e);
        }
        return mantenimientosPorMatricula;
    }

    @Override
    public Optional<Mantenimiento> buscarPorId(int id) {
        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection
                        .prepareStatement("SELECT * FROM mantenimientos WHERE id = ?")) {

            sentencia.setInt(1, id);

            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return Optional.of(mapRow(resultado));
            }

        } catch (Exception e) {
            System.err.println("Error buscando mantenimiento por id " + e);
        }

        return Optional.empty();
    }

    @Override
    public boolean actualizar(Mantenimiento mantenimiento) {
        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(
                        "UPDATE mantenimientos SET fecha_revision = ?, descripcion = ?, coste = ?, km_en_la_revision = ? WHERE id = ?")) {

            sentencia.setString(1, mantenimiento.getFechaRevision().toString());
            sentencia.setString(2, mantenimiento.getDescripcion());
            sentencia.setDouble(3, mantenimiento.getCoste());
            sentencia.setInt(4, mantenimiento.getKmEnLaRevision());
            sentencia.setInt(5, mantenimiento.getId());

            return sentencia.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error actualizando el mantenimiento" + e);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection
                        .prepareStatement("DELETE FROM mantenimientos WHERE id = ?")) {

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error eliminando mantenimiento por id" + e);
            return false;
        }
    }

    @Override
    public void eliminarPorMatricula(String matricula) {
        try (Connection connection = this.getConnection();
                PreparedStatement sentencia = connection
                        .prepareStatement("DELETE FROM mantenimientos WHERE matricula = ?")) {

            sentencia.setString(1, matricula);
            sentencia.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error eliminando mantenimientos por matricula" + e);
        }
    }

    private Mantenimiento mapRow(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String matricula = resultado.getString("matricula");
        LocalDate fechaRevision = LocalDate.parse(resultado.getString("fecha_revision"));
        String descripcion = resultado.getString("descripcion");
        double coste = resultado.getDouble("coste");
        int kmEnLaRevision = resultado.getInt("km_en_la_revision");

        return new Mantenimiento(id, matricula, fechaRevision, descripcion, coste, kmEnLaRevision);
    }

}
