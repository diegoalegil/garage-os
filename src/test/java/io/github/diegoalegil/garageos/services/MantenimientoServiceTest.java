package io.github.diegoalegil.garageos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.github.diegoalegil.garageos.models.Mantenimiento;
import io.github.diegoalegil.garageos.repositories.MantenimientoMemoryRepository;

class MantenimientoServiceTest {

    @Test
    void guardarMantenimientoLoDejaDisponiblePorMatricula() {
        MantenimientoMemoryRepository repository = new MantenimientoMemoryRepository();
        MantenimientoService service = new MantenimientoService(repository);
        Mantenimiento mantenimiento = crearMantenimiento("2325HTM");

        assertTrue(service.guardarMantenimiento(mantenimiento));
        assertEquals(1, service.obtenerPorMatricula("2325HTM").size());
    }

    @Test
    void actualizarMantenimientoCambiaLosDatosGuardados() {
        MantenimientoMemoryRepository repository = new MantenimientoMemoryRepository();
        MantenimientoService service = new MantenimientoService(repository);
        Mantenimiento mantenimiento = crearMantenimiento("2325HTM");
        service.guardarMantenimiento(mantenimiento);

        Mantenimiento mantenimientoEditado = new Mantenimiento(
                mantenimiento.getId(),
                "2325HTM",
                LocalDate.of(2026, 6, 1),
                "Cambio de filtros",
                120.50,
                162000);

        assertTrue(service.actualizarMantenimiento(mantenimientoEditado));
        assertEquals("Cambio de filtros", service.buscarPorId(mantenimiento.getId()).get().getDescripcion());
    }

    @Test
    void eliminarMantenimientoBorraElRegistro() {
        MantenimientoMemoryRepository repository = new MantenimientoMemoryRepository();
        MantenimientoService service = new MantenimientoService(repository);
        Mantenimiento mantenimiento = crearMantenimiento("2325HTM");
        service.guardarMantenimiento(mantenimiento);

        assertTrue(service.eliminarMantenimiento(mantenimiento.getId()));
        assertFalse(service.buscarPorId(mantenimiento.getId()).isPresent());
    }

    private Mantenimiento crearMantenimiento(String matricula) {
        return new Mantenimiento(
                matricula,
                LocalDate.of(2026, 5, 31),
                "Cambio de aceite",
                89.99,
                161000);
    }
}
