package io.github.diegoalegil.garageos.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.github.diegoalegil.garageos.models.TipoPropulsion;

class ValidacionTest {

    @Test
    void validarMatriculaDevuelveErrorCuandoEstaVacia() {
        assertEquals("La matrícula no puede estar vacía", Validacion.validarMatricula(" "));
    }

    @Test
    void validarMatriculaDevuelveVacioCuandoEsValida() {
        assertEquals("", Validacion.validarMatricula("2325HTM"));
    }

    @Test
    void validarMatriculaDevuelveVacioCuandoTieneMinusculas() {
        assertEquals("", Validacion.validarMatricula("2325htm"));
    }

    @Test
    void validarMatriculaDevuelveVacioCuandoTieneTresNumeros() {
        assertEquals("", Validacion.validarMatricula("999BCD"));
    }

    @Test
    void validarMatriculaDevuelveErrorCuandoNoTieneFormatoCorrecto() {
        assertEquals("La matrícula debe tener formato 123ABC o 1234ABC", Validacion.validarMatricula("HTM2325"));
    }

    @Test
    void validarMarcaDevuelveErrorCuandoEstaVacia() {
        assertEquals("La marca no puede estar vacía", Validacion.validarMarca(""));
    }

    @Test
    void validarModeloDevuelveErrorCuandoEstaVacio() {
        assertEquals("El modelo no puede estar vacío", Validacion.validarModelo(null));
    }

    @Test
    void validarAnioDevuelveErrorCuandoEstaFueraDeRango() {
        assertEquals("El año debe estar entre 1900 y 2100", Validacion.validarAnio(1800));
    }

    @Test
    void validarAnioDevuelveVacioCuandoEstaEnRango() {
        assertEquals("", Validacion.validarAnio(2024));
    }

    @Test
    void validarKilometrajeDevuelveErrorCuandoEsNegativo() {
        assertEquals("El kilometraje no puede ser negativo", Validacion.validarKilometraje(-1));
    }

    @Test
    void validarPropulsionDevuelveErrorCuandoNoHaySeleccion() {
        assertEquals("Debes seleccionar un tipo de propulsión", Validacion.validarPropulsion(null));
    }

    @Test
    void validarPropulsionDevuelveVacioCuandoHaySeleccion() {
        assertEquals("", Validacion.validarPropulsion(TipoPropulsion.DIESEL));
    }

    @Test
    void validarFechaMantenimientoDevuelveErrorCuandoEsFutura() {
        LocalDate fechaFutura = LocalDate.now().plusDays(1);

        assertEquals("La fecha del mantenimiento no puede ser futura",
                Validacion.validarFechaMantenimiento(fechaFutura));
    }

    @Test
    void validarDescripcionMantenimientoDevuelveErrorCuandoEstaVacia() {
        assertEquals("La descripción del mantenimiento no puede estar vacía",
                Validacion.validarDescripcionMantenimiento(" "));
    }

    @Test
    void validarCosteMantenimientoDevuelveErrorCuandoEsNegativo() {
        assertEquals("El coste del mantenimiento no puede ser negativo",
                Validacion.validarCosteMantenimiento(-10));
    }

    @Test
    void validarKmMantenimientoDevuelveErrorCuandoSuperaKmDelVehiculo() {
        assertEquals("Los km del mantenimiento no pueden superar los km del vehículo",
                Validacion.validarKmMantenimiento(120000, 100000));
    }

    @Test
    void validarKmMantenimientoDevuelveVacioCuandoEsCorrecto() {
        assertEquals("", Validacion.validarKmMantenimiento(90000, 100000));
    }
}
