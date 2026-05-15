package io.github.diegoalegil.garageos.controllers;

import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.services.VehiculoService;
import io.github.diegoalegil.garageos.utils.Validacion;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrincipalController {

    @FXML
    private TextField matriculaField;

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private TextField anioField;

    @FXML
    private TextField kilometrajeField;

    @FXML
    private ComboBox<TipoPropulsion> propulsionCombo;

    @FXML
    private Label resultadoLabel;

    private final VehiculoService servicio = new VehiculoService();

    @FXML
    public void initialize() {
        propulsionCombo.getItems().addAll(TipoPropulsion.values());
    }

    @FXML
    private void guardarVehiculo() {
        try {
            String matricula = matriculaField.getText();
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            int anio = Integer.parseInt(anioField.getText());
            int kilometraje = Integer.parseInt(kilometrajeField.getText());
            TipoPropulsion propulsion = propulsionCombo.getValue();

            String errorMatricula = Validacion.validarMatricula(matricula);
            if (!errorMatricula.isEmpty()) {
                resultadoLabel.setText(errorMatricula);
                return;
            }

            String errorMarca = Validacion.validarMarca(marca);
            if (!errorMarca.isEmpty()) {
                resultadoLabel.setText(errorMarca);
                return;
            }

            String errorModelo = Validacion.validarModelo(modelo);
            if (!errorModelo.isEmpty()) {
                resultadoLabel.setText(errorModelo);
                return;
            }

            String errorAnio = Validacion.validarAnio(anio);
            if (!errorAnio.isEmpty()) {
                resultadoLabel.setText(errorAnio);
                return;
            }

            String errorKilometraje = Validacion.validarKilometraje(kilometraje);
            if (!errorKilometraje.isEmpty()) {
                resultadoLabel.setText(errorKilometraje);
                return;
            }

            String errorPropulsion = Validacion.validarPropulsion(propulsion);
            if (!errorPropulsion.isEmpty()) {
                resultadoLabel.setText(errorPropulsion);
                return;
            }

            Vehiculo vehiculo = new Vehiculo(matricula, marca, modelo, anio, kilometraje, propulsion);
            servicio.guardarVehiculo(vehiculo);

            resultadoLabel.setText("Guardado: " + vehiculo);
        } catch (NumberFormatException e) {
            resultadoLabel.setText("Error: año y kilometraje deben ser números");
        }
    }
}