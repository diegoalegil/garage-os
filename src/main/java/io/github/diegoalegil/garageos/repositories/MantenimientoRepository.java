package io.github.diegoalegil.garageos.repositories;

import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Mantenimiento;

public interface MantenimientoRepository {

    boolean guardar(Mantenimiento mantenimiento);

    List<Mantenimiento> obtenerTodos();

    List<Mantenimiento> obtenerPorMatricula(String matricula);

    Optional<Mantenimiento> buscarPorId(int id);

    boolean actualizar(Mantenimiento mantenimiento);

    boolean eliminar(int id);

    void eliminarPorMatricula(String matricula);
}
