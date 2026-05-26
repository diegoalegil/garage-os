package io.github.diegoalegil.garageos.repositories;

import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Mantenimiento;

public interface MantenimientoRepository {

    void guardar(Mantenimiento mantenimiento); // INSERT

    List<Mantenimiento> obtenerPorMatricula(String matricula); // SELECT WHERE

    Optional<Mantenimiento> buscarPorId(int id); // SELECT por id

    boolean actualizar(Mantenimiento mantenimiento); // UPDATE

    boolean eliminar(int id);
}
