package io.github.diegoalegil.garageos.controllers;

import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.services.VehiculoService;
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

            Vehiculo vehiculo = new Vehiculo(matricula, marca, modelo, anio, kilometraje, propulsion);
            servicio.guardarVehiculo(vehiculo);

            resultadoLabel.setText("Guardado: " + vehiculo);
        } catch (NumberFormatException e) {
            resultadoLabel.setText("Error: año y kilometraje deben ser números");
        }
    }
}