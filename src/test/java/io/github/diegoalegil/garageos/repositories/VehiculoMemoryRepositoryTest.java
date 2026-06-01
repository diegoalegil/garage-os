package io.github.diegoalegil.garageos.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;

class VehiculoMemoryRepositoryTest {

    @Test
    void guardarDevuelveFalseCuandoLaMatriculaYaExiste() {
        VehiculoMemoryRepository repository = new VehiculoMemoryRepository();
        Vehiculo vehiculo = new Vehiculo("2325HTM", "Mercedes", "C-Class", 2013, 161000,
                TipoPropulsion.DIESEL);
        Vehiculo vehiculoDuplicado = new Vehiculo("2325HTM", "Mercedes", "C-Class", 2013, 161000,
                TipoPropulsion.DIESEL);

        assertTrue(repository.guardar(vehiculo));
        assertFalse(repository.guardar(vehiculoDuplicado));
    }
}
