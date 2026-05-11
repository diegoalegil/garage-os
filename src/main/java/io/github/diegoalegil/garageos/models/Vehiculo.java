package io.github.diegoalegil.garageos.models;

public class Vehiculo {

    private final String matricula;
    private String marca;
    private String modelo;
    private int anio;
    private int kilometraje;
    private TipoPropulsion tipoPropulsion;

    public Vehiculo(String matricula) {
        this.matricula = matricula;
    }

    public Vehiculo(String matricula, String marca, String modelo, int anio, int kilometraje,
            TipoPropulsion tipoPropulsion) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.kilometraje = kilometraje;
        this.tipoPropulsion = tipoPropulsion;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public TipoPropulsion getTipoPropulsion() {
        return tipoPropulsion;
    }

    public void setTipoPropulsion(TipoPropulsion tipoPropulsion) {
        this.tipoPropulsion = tipoPropulsion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
        Vehiculo other = (Vehiculo) obj;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", anio=" + anio
                + ", kilometraje=" + kilometraje + ", tipoPropulsion=" + tipoPropulsion + "]";
    }

}
