package io.github.diegoalegil.garageos.utils;

import java.time.LocalDate;

import io.github.diegoalegil.garageos.models.TipoPropulsion;

public class Validacion {

    public static String validarMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return "La matrícula no puede estar vacía";
        }
        return "";
    }

    public static String validarMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            return "La marca no puede estar vacía";
        }
        return "";
    }

    public static String validarModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            return "El modelo no puede estar vacío";
        }
        return "";
    }

    public static String validarAnio(int anio) {
        if (anio < 1900 || anio > 2100) {
            return "El año debe estar entre 1900 y 2100";
        }
        return "";
    }

    public static String validarKilometraje(int kilometraje) {
        if (kilometraje < 0) {
            return "El kilometraje no puede ser negativo";
        }
        return "";
    }

    public static String validarPropulsion(TipoPropulsion propulsion) {
        if (propulsion == null) {
            return "Debes seleccionar un tipo de propulsión";
        }
        return "";
    }

    public static String validarFechaMantenimiento(LocalDate fecha) {
        if (fecha == null) {
            return "La fecha del mantenimiento no puede estar vacía";
        }
        if (fecha.isAfter(LocalDate.now())) {
            return "La fecha del mantenimiento no puede ser futura";
        }
        return "";
    }

    public static String validarDescripcionMantenimiento(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return "La descripción del mantenimiento no puede estar vacía";
        }
        return "";
    }

    public static String validarCosteMantenimiento(double coste) {
        if (coste < 0) {
            return "El coste del mantenimiento no puede ser negativo";
        }
        return "";
    }

    public static String validarKmMantenimiento(int kmEnLaRevision, int kilometrajeVehiculo) {
        if (kmEnLaRevision < 0) {
            return "Los km del mantenimiento no pueden ser negativos";
        }
        if (kmEnLaRevision > kilometrajeVehiculo) {
            return "Los km del mantenimiento no pueden superar los km del vehículo";
        }
        return "";
    }
}
