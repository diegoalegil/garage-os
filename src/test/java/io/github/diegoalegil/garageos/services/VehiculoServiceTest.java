package io.github.diegoalegil.garageos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.github.diegoalegil.garageos.models.Mantenimiento;
import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.repositories.MantenimientoMemoryRepository;
import io.github.diegoalegil.garageos.repositories.VehiculoMemoryRepository;

class VehiculoServiceTest {

    @Test
    void eliminarVehiculoTambienBorraSusMantenimientos() {
        VehiculoMemoryRepository vehiculoRepository = new VehiculoMemoryRepository();
        MantenimientoMemoryRepository mantenimientoRepository = new MantenimientoMemoryRepository();
        VehiculoService vehiculoService = new VehiculoService(vehiculoRepository, mantenimientoRepository);

        Vehiculo vehiculo = new Vehiculo("2325HTM", "Mercedes", "C-Class", 2013, 161000,
                TipoPropulsion.DIESEL);
        Mantenimiento mantenimiento = new Mantenimiento("2325HTM", LocalDate.of(2026, 5, 31),
                "Cambio de aceite", 89.99, 161000);

        vehiculoService.guardarVehiculo(vehiculo);
        mantenimientoRepository.guardar(mantenimiento);

        assertTrue(vehiculoService.eliminar("2325HTM"));
        assertFalse(vehiculoService.buscarPorMatricula("2325HTM").isPresent());
        assertEquals(0, mantenimientoRepository.obtenerPorMatricula("2325HTM").size());
    }
}
