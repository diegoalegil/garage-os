package io.github.diegoalegil.garageos.repositories;

import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Vehiculo;

public interface VehiculoRepository {

    boolean guardar(Vehiculo vehiculo);

    List<Vehiculo> obtenerTodos();

    Optional<Vehiculo> buscarPorMatricula(String matricula);

    boolean actualizar(Vehiculo vehiculo);

    boolean eliminar(String matricula);

}
