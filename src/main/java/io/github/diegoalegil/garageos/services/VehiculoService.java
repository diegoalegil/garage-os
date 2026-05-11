package io.github.diegoalegil.garageos.services;

import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.repositories.VehiculoMemoryRepository;
import io.github.diegoalegil.garageos.repositories.VehiculoRepository;

public class VehiculoService {

    private final VehiculoRepository repository;

    public VehiculoService() {
        this.repository = new VehiculoMemoryRepository();
    }

    public void guardarVehiculo(Vehiculo vehiculo) {
        repository.guardar(vehiculo);
    }

    public List<Vehiculo> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public Optional<Vehiculo> buscarPorMatricula(String matricula) {
        return repository.buscarPorMatricula(matricula);
    }

    public boolean eliminar(String matricula) {
        return repository.eliminar(matricula);
    }

}
