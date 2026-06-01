package io.github.diegoalegil.garageos.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Mantenimiento;

public class MantenimientoMemoryRepository implements MantenimientoRepository {

    private final List<Mantenimiento> mantenimientos = new ArrayList<>();
    private int siguienteId = 1;

    @Override
    public boolean guardar(Mantenimiento mantenimiento) {
        mantenimiento.setId(siguienteId);
        siguienteId++;
        mantenimientos.add(mantenimiento);
        return true;
    }

    @Override
    public List<Mantenimiento> obtenerTodos() {
        return new ArrayList<>(mantenimientos);
    }

    @Override
    public List<Mantenimiento> obtenerPorMatricula(String matricula) {
        return mantenimientos.stream()
                .filter(mantenimiento -> mantenimiento.getMatricula().equals(matricula))
                .toList();
    }

    @Override
    public Optional<Mantenimiento> buscarPorId(int id) {
        return mantenimientos.stream()
                .filter(mantenimiento -> mantenimiento.getId() == id)
                .findFirst();
    }

    @Override
    public boolean actualizar(Mantenimiento mantenimiento) {
        int posicion = mantenimientos.indexOf(mantenimiento);
        if (posicion == -1) {
            return false;
        }
        mantenimientos.set(posicion, mantenimiento);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        return mantenimientos.removeIf(mantenimiento -> mantenimiento.getId() == id);
    }

    @Override
    public void eliminarPorMatricula(String matricula) {
        mantenimientos.removeIf(mantenimiento -> mantenimiento.getMatricula().equals(matricula));
    }
}
