package io.github.diegoalegil.garageos.controllers;

import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.services.VehiculoService;
import io.github.diegoalegil.garageos.utils.Validacion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

    @FXML
    private Button accionButton;

    @FXML
    private ListView<Vehiculo> vehiculosList;

    private final VehiculoService servicio = new VehiculoService();

    @FXML
    public void initialize() {
        propulsionCombo.getItems().addAll(TipoPropulsion.values());
        refrescarLista();
        vehiculosList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                matriculaField.setText(newVal.getMatricula());
                marcaField.setText(newVal.getMarca());
                modeloField.setText(newVal.getModelo());
                anioField.setText(String.valueOf(newVal.getAnio()));
                kilometrajeField.setText(String.valueOf(newVal.getKilometraje()));
                propulsionCombo.setValue(newVal.getTipoPropulsion());
                matriculaField.setDisable(true);
                accionButton.setText("Actualizar");
            }
        });
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

            if (matriculaField.isDisabled()) {
                // Modo edición: actualizar
                servicio.actualizarVehiculo(vehiculo);
                resultadoLabel.setText("Actualizado: " + vehiculo);
            } else {
                // Modo creación: guardar
                servicio.guardarVehiculo(vehiculo);
                resultadoLabel.setText("Guardado: " + vehiculo);
            }

            refrescarLista();
            cancelarEdicion();

        } catch (NumberFormatException e) {
            resultadoLabel.setText("Error: año y kilometraje deben ser números");
        }
    }

    private void refrescarLista() {

        vehiculosList.getItems().clear();

        vehiculosList.getItems().addAll(servicio.obtenerTodos());
    }

    @FXML
    private void cancelarEdicion() {
        matriculaField.clear();
        marcaField.clear();
        modeloField.clear();
        anioField.clear();
        kilometrajeField.clear();
        propulsionCombo.setValue(null);
        matriculaField.setDisable(false);
        accionButton.setText("Guardar");
        vehiculosList.getSelectionModel().clearSelection();
    }
}