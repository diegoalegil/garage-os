package io.github.diegoalegil.garageos.services;

import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.repositories.MantenimientoRepository;
import io.github.diegoalegil.garageos.repositories.VehiculoRepository;
import io.github.diegoalegil.garageos.repositories.sqlite.MantenimientoSqliteRepository;
import io.github.diegoalegil.garageos.repositories.sqlite.VehiculoSqliteRepository;

public class VehiculoService {

    private final VehiculoRepository repository;
    private final MantenimientoRepository mantenimientoRepository;

    public VehiculoService() {
        this.repository = new VehiculoSqliteRepository();
        this.mantenimientoRepository = new MantenimientoSqliteRepository();
    }

    public boolean guardarVehiculo(Vehiculo vehiculo) {
        return repository.guardar(vehiculo);
    }

    public List<Vehiculo> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public Optional<Vehiculo> buscarPorMatricula(String matricula) {
        return repository.buscarPorMatricula(matricula);
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        return repository.actualizar(vehiculo);
    }

    public boolean eliminar(String matricula) {
        mantenimientoRepository.eliminarPorMatricula(matricula);
        return repository.eliminar(matricula);
    }

}
