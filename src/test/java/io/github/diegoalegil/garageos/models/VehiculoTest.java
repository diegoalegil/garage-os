package io.github.diegoalegil.garageos.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VehiculoTest {

    @Test
    void toStringMuestraDatosFormateadosParaLaLista() {
        Vehiculo vehiculo = new Vehiculo(
                "2325HTM",
                "Mercedes",
                "C-Class",
                2013,
                161000,
                TipoPropulsion.DIESEL);

        assertEquals("2325HTM · Mercedes C-Class (2013) · 161.000 km · DIESEL", vehiculo.toString());
    }
}
