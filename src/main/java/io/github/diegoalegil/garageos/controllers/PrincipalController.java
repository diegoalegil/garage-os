package io.github.diegoalegil.garageos.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import io.github.diegoalegil.garageos.models.Mantenimiento;
import io.github.diegoalegil.garageos.models.TipoPropulsion;
import io.github.diegoalegil.garageos.models.Vehiculo;
import io.github.diegoalegil.garageos.services.MantenimientoService;
import io.github.diegoalegil.garageos.services.VehiculoService;
import io.github.diegoalegil.garageos.utils.Validacion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    @FXML
    private TextField fechaMantenimientoField;

    @FXML
    private TextField descripcionMantenimientoField;

    @FXML
    private TextField costeMantenimientoField;

    @FXML
    private TextField kmMantenimientoField;

    @FXML
    private Button guardarMantenimientoButton;

    @FXML
    private ListView<Mantenimiento> mantenimientosList;

    @FXML
    private Label resultadoMantenimientoLabel;

    private final VehiculoService servicio = new VehiculoService();

    private final MantenimientoService mantenimientoService = new MantenimientoService();

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
                refrescarMantenimientos(newVal.getMatricula());
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

    private void refrescarMantenimientos(String matricula) {
        mantenimientosList.getItems().clear();
        mantenimientosList.getItems().addAll(mantenimientoService.obtenerPorMatricula(matricula));
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

    @FXML
    private void eliminarVehiculo() {
        Vehiculo seleccionado = vehiculosList.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            resultadoLabel.setText("Selecciona un vehiculo para eliminar");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminacion");
        alerta.setHeaderText("¿Eliminar este vehiculo?");
        alerta.setContentText(seleccionado.toString());

        Optional<ButtonType> respuesta = alerta.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            boolean eliminado = servicio.eliminar(seleccionado.getMatricula());
            if (eliminado) {
                resultadoLabel.setText("Eliminado: " + seleccionado);
                refrescarLista();
                cancelarEdicion();
            } else {
                resultadoLabel.setText("No se pudo eliminar");
            }

        }
    }

    @FXML
    private void guardarMantenimiento() {
        Vehiculo seleccionado = vehiculosList.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            resultadoMantenimientoLabel.setText("Selecciona un vehículo primero");
            return;
        }

        try {
            LocalDate fechaRevision = LocalDate.parse(fechaMantenimientoField.getText());
            String descripcion = descripcionMantenimientoField.getText();
            double coste = Double.parseDouble(costeMantenimientoField.getText());
            int kmEnLaRevision = Integer.parseInt(kmMantenimientoField.getText());

            Mantenimiento mantenimiento = new Mantenimiento(
                    seleccionado.getMatricula(),
                    fechaRevision,
                    descripcion,
                    coste,
                    kmEnLaRevision);

            mantenimientoService.guardarMantenimiento(mantenimiento);
            resultadoMantenimientoLabel.setText("Mantenimiento guardado");

            refrescarMantenimientos(seleccionado.getMatricula());
            limpiarFormularioMantenimiento();

        } catch (DateTimeParseException e) {
            resultadoMantenimientoLabel.setText("Fecha inválida. Usa YYYY-MM-DD");
        } catch (NumberFormatException e) {
            resultadoMantenimientoLabel.setText("Coste y km deben ser números");
        }
    }

    private void limpiarFormularioMantenimiento() {
        fechaMantenimientoField.clear();
        descripcionMantenimientoField.clear();
        costeMantenimientoField.clear();
        kmMantenimientoField.clear();
    }
}
