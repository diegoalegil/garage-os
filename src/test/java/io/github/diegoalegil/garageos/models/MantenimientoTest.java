package io.github.diegoalegil.garageos.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MantenimientoTest {

    @Test
    void toStringMuestraDatosFormateadosParaLaLista() {
        Mantenimiento mantenimiento = new Mantenimiento(
                "2325HTM",
                LocalDate.of(2026, 5, 31),
                "Cambio de aceite",
                89.99,
                161000);

        assertEquals("2026-05-31 · Cambio de aceite · 89,99 € · 161.000 km", mantenimiento.toString());
    }
}
