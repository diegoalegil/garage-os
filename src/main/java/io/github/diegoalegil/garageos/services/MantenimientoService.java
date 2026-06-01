package io.github.diegoalegil.garageos.services;

import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Mantenimiento;
import io.github.diegoalegil.garageos.repositories.MantenimientoRepository;
import io.github.diegoalegil.garageos.repositories.sqlite.MantenimientoSqliteRepository;

public class MantenimientoService {

    private final MantenimientoRepository repository;

    public MantenimientoService() {
        this.repository = new MantenimientoSqliteRepository();
    }

    public boolean guardarMantenimiento(Mantenimiento mantenimiento) {
        return repository.guardar(mantenimiento);
    }

    public List<Mantenimiento> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public List<Mantenimiento> obtenerPorMatricula(String matricula) {
        return repository.obtenerPorMatricula(matricula);
    }

    public Optional<Mantenimiento> buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public boolean actualizarMantenimiento(Mantenimiento mantenimiento) {
        return repository.actualizar(mantenimiento);
    }

    public boolean eliminarMantenimiento(int id) {
        return repository.eliminar(id);
    }

    public void eliminarPorMatricula(String matricula) {
        repository.eliminarPorMatricula(matricula);
    }
}
