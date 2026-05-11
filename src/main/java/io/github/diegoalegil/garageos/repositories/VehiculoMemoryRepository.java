package io.github.diegoalegil.garageos.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Vehiculo;

public class VehiculoMemoryRepository implements VehiculoRepository {

    List<Vehiculo> vehiculos = new ArrayList<>();

    @Override
    public void guardar(Vehiculo vehiculo) {

        vehiculos.add(vehiculo);
    }

    @Override
    public List<Vehiculo> obtenerTodos() {

        return new ArrayList<>(vehiculos);
    }

    @Override
    public Optional<Vehiculo> buscarPorMatricula(String matricula) {

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return Optional.of(vehiculo);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminar(String matricula) {

        Vehiculo vehiculo = new Vehiculo(matricula);

        if (!vehiculos.contains(vehiculo)) {
            return false;
        }

        int posicion = vehiculos.indexOf(vehiculo);

        vehiculos.remove(posicion);

        return true;
    }

}
