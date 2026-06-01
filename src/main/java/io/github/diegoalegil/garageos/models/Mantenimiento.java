package io.github.diegoalegil.garageos.models;

import java.time.LocalDate;
import java.util.Locale;

public class Mantenimiento {

    private int id;
    private final String matricula;
    private LocalDate fechaRevision;
    private String descripcion;
    private double coste;
    private int kmEnLaRevision;

    public Mantenimiento(String matricula, LocalDate fechaRevision, String descripcion, double coste,
            int kmEnLaRevision) {
        this.matricula = matricula;
        this.fechaRevision = fechaRevision;
        this.descripcion = descripcion;
        this.coste = coste;
        this.kmEnLaRevision = kmEnLaRevision;
    }

    public Mantenimiento(int id, String matricula, LocalDate fechaRevision, String descripcion, double coste,
            int kmEnLaRevision) {
        this.id = id;
        this.matricula = matricula;
        this.fechaRevision = fechaRevision;
        this.descripcion = descripcion;
        this.coste = coste;
        this.kmEnLaRevision = kmEnLaRevision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public LocalDate getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(LocalDate fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public int getKmEnLaRevision() {
        return kmEnLaRevision;
    }

    public void setKmEnLaRevision(int kmEnLaRevision) {
        this.kmEnLaRevision = kmEnLaRevision;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mantenimiento other = (Mantenimiento) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format(Locale.of("es", "ES"), "%s · %s · %.2f € · %,d km",
                fechaRevision, descripcion, coste, kmEnLaRevision);
    }

}
