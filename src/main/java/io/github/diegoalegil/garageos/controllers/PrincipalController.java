package io.github.diegoalegil.garageos.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
    private TextField buscarVehiculoField;

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
    private Label resumenVehiculosLabel;

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
    private Button cancelarMantenimientoButton;

    @FXML
    private Button eliminarMantenimientoButton;

    @FXML
    private ListView<Mantenimiento> mantenimientosList;

    @FXML
    private Label resultadoMantenimientoLabel;

    @FXML
    private Label resumenMantenimientosLabel;

    @FXML
    private Label ultimoMantenimientoLabel;

    @FXML
    private Label vehiculoActivoLabel;

    @FXML
    private Label vehiculoActivoDetalleLabel;

    @FXML
    private Label proximaRevisionLabel;

    @FXML
    private Label dashboardVehiculosLabel;

    @FXML
    private Label dashboardMantenimientosLabel;

    @FXML
    private Label dashboardInversionLabel;

    @FXML
    private Label dashboardUltimoLabel;

    private final VehiculoService servicio = new VehiculoService();

    private final MantenimientoService mantenimientoService = new MantenimientoService();

    private final List<Vehiculo> vehiculosCargados = new ArrayList<>();

    private static final int INTERVALO_REVISION_KM = 15000;

    @FXML
    public void initialize() {
        propulsionCombo.getItems().addAll(TipoPropulsion.values());
        configurarPlaceholders();
        limpiarFichaVehiculo();
        configurarEstadoFormularioMantenimiento(false);
        refrescarLista();
        buscarVehiculoField.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltroVehiculos());
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
                limpiarFormularioMantenimiento();
                prepararNuevoMantenimiento();
                configurarEstadoFormularioMantenimiento(true);
            } else {
                mantenimientosList.getItems().clear();
                limpiarFormularioMantenimiento();
                limpiarFichaVehiculo();
                configurarEstadoFormularioMantenimiento(false);
                actualizarResumenMantenimientos();
            }
        });

        mantenimientosList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fechaMantenimientoField.setText(newVal.getFechaRevision().toString());
                descripcionMantenimientoField.setText(newVal.getDescripcion());
                costeMantenimientoField.setText(String.valueOf(newVal.getCoste()));
                kmMantenimientoField.setText(String.valueOf(newVal.getKmEnLaRevision()));
                guardarMantenimientoButton.setText("Actualizar mant.");
                eliminarMantenimientoButton.setDisable(false);
            } else {
                eliminarMantenimientoButton.setDisable(true);
            }
        });
        actualizarResumenMantenimientos();
    }

    private void configurarPlaceholders() {
        Label vehiculosPlaceholder = new Label("No hay vehículos registrados");
        vehiculosPlaceholder.getStyleClass().add("empty-state-label");
        vehiculosList.setPlaceholder(vehiculosPlaceholder);

        Label mantenimientosPlaceholder = new Label("Selecciona un vehículo o añade su primer mantenimiento");
        mantenimientosPlaceholder.getStyleClass().add("empty-state-label");
        mantenimientosList.setPlaceholder(mantenimientosPlaceholder);
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
                boolean actualizado = servicio.actualizarVehiculo(vehiculo);
                if (!actualizado) {
                    resultadoLabel.setText("No se pudo actualizar el vehículo");
                    return;
                }
                resultadoLabel.setText("Actualizado: " + vehiculo);
            } else {
                // Modo creación: guardar
                boolean guardado = servicio.guardarVehiculo(vehiculo);
                if (!guardado) {
                    resultadoLabel.setText("No se pudo guardar. Revisa si la matrícula ya existe");
                    return;
                }
                resultadoLabel.setText("Guardado: " + vehiculo);
            }

            refrescarLista();
            cancelarEdicion();

        } catch (NumberFormatException e) {
            resultadoLabel.setText("Error: año y kilometraje deben ser números");
        }
    }

    private void refrescarLista() {
        vehiculosCargados.clear();
        vehiculosCargados.addAll(servicio.obtenerTodos());
        aplicarFiltroVehiculos();
        actualizarDashboard();
    }

    private void aplicarFiltroVehiculos() {
        String filtro = buscarVehiculoField.getText().trim().toLowerCase(Locale.ROOT);
        vehiculosList.getItems().clear();

        if (filtro.isEmpty()) {
            vehiculosList.getItems().addAll(vehiculosCargados);
        } else {
            vehiculosList.getItems().addAll(vehiculosCargados.stream()
                    .filter(vehiculo -> coincideConFiltro(vehiculo, filtro))
                    .toList());
        }

        actualizarResumenVehiculos();
    }

    private boolean coincideConFiltro(Vehiculo vehiculo, String filtro) {
        return vehiculo.getMatricula().toLowerCase(Locale.ROOT).contains(filtro)
                || vehiculo.getMarca().toLowerCase(Locale.ROOT).contains(filtro)
                || vehiculo.getModelo().toLowerCase(Locale.ROOT).contains(filtro)
                || vehiculo.getTipoPropulsion().name().toLowerCase(Locale.ROOT).contains(filtro);
    }

    private void actualizarResumenVehiculos() {
        int visibles = vehiculosList.getItems().size();
        int total = vehiculosCargados.size();

        if (total == 0) {
            resumenVehiculosLabel.setText("Sin vehículos registrados");
        } else if (visibles == total) {
            resumenVehiculosLabel.setText(total + " vehículos registrados");
        } else {
            resumenVehiculosLabel.setText(visibles + " de " + total + " vehículos");
        }
    }

    private void refrescarMantenimientos(String matricula) {
        mantenimientosList.getItems().clear();
        mantenimientosList.getItems().addAll(mantenimientoService.obtenerPorMatricula(matricula));
        actualizarFichaVehiculo(vehiculosList.getSelectionModel().getSelectedItem());
        actualizarResumenMantenimientos();
        actualizarDashboard();
    }

    private void actualizarDashboard() {
        List<Mantenimiento> mantenimientos = mantenimientoService.obtenerTodos();
        double inversionTotal = mantenimientos.stream()
                .mapToDouble(Mantenimiento::getCoste)
                .sum();

        dashboardVehiculosLabel.setText(String.valueOf(vehiculosCargados.size()));
        dashboardMantenimientosLabel.setText(String.valueOf(mantenimientos.size()));
        dashboardInversionLabel.setText(String.format(Locale.of("es", "ES"), "%.2f €", inversionTotal));
        dashboardUltimoLabel.setText(obtenerTextoUltimoMantenimiento(mantenimientos));
    }

    private String obtenerTextoUltimoMantenimiento(List<Mantenimiento> mantenimientos) {
        return mantenimientos.stream()
                .max(Comparator.comparing(Mantenimiento::getFechaRevision)
                        .thenComparing(Mantenimiento::getId))
                .map(mantenimiento -> mantenimiento.getFechaRevision() + " · " + mantenimiento.getMatricula())
                .orElse("Sin historial");
    }

    private void actualizarResumenMantenimientos() {
        Vehiculo vehiculoSeleccionado = vehiculosList.getSelectionModel().getSelectedItem();
        int total = mantenimientosList.getItems().size();

        if (vehiculoSeleccionado == null) {
            resumenMantenimientosLabel.setText("Selecciona un vehículo");
            ultimoMantenimientoLabel.setText("");
        } else if (total == 0) {
            resumenMantenimientosLabel.setText("Sin mantenimientos registrados");
            ultimoMantenimientoLabel.setText("Aún no hay historial para este vehículo");
        } else if (total == 1) {
            resumenMantenimientosLabel.setText("1 mantenimiento · " + formatearCosteTotalMantenimientos());
            actualizarUltimoMantenimiento();
        } else {
            resumenMantenimientosLabel.setText(total + " mantenimientos · " + formatearCosteTotalMantenimientos());
            actualizarUltimoMantenimiento();
        }
    }

    private String formatearCosteTotalMantenimientos() {
        double costeTotal = mantenimientosList.getItems().stream()
                .mapToDouble(Mantenimiento::getCoste)
                .sum();

        return String.format(Locale.of("es", "ES"), "%.2f € invertidos", costeTotal);
    }

    private void actualizarUltimoMantenimiento() {
        mantenimientosList.getItems().stream()
                .max(Comparator.comparing(Mantenimiento::getFechaRevision)
                        .thenComparing(Mantenimiento::getId))
                .ifPresent(ultimo -> ultimoMantenimientoLabel.setText(
                        "Último: " + ultimo.getFechaRevision() + " · " + ultimo.getDescripcion()));
    }

    private void actualizarFichaVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            limpiarFichaVehiculo();
            return;
        }

        vehiculoActivoLabel.setText(vehiculo.getMatricula() + " · " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        vehiculoActivoDetalleLabel.setText(String.format(Locale.of("es", "ES"), "%d · %,d km · %s",
                vehiculo.getAnio(), vehiculo.getKilometraje(), vehiculo.getTipoPropulsion()));
        proximaRevisionLabel.setText("Próxima revisión sugerida: " + formatearKm(calcularProximaRevisionKm(vehiculo)));
    }

    private int calcularProximaRevisionKm(Vehiculo vehiculo) {
        int kmUltimoMantenimiento = mantenimientosList.getItems().stream()
                .mapToInt(Mantenimiento::getKmEnLaRevision)
                .max()
                .orElse(vehiculo.getKilometraje());

        int kmReferencia = Math.max(vehiculo.getKilometraje(), kmUltimoMantenimiento);
        return kmReferencia + INTERVALO_REVISION_KM;
    }

    private String formatearKm(int kilometros) {
        return String.format(Locale.of("es", "ES"), "%,d km", kilometros);
    }

    private void limpiarFichaVehiculo() {
        vehiculoActivoLabel.setText("Selecciona un vehículo");
        vehiculoActivoDetalleLabel.setText("El historial se mostrará aquí");
        proximaRevisionLabel.setText("Próxima revisión: --");
    }

    private void configurarEstadoFormularioMantenimiento(boolean hayVehiculoSeleccionado) {
        fechaMantenimientoField.setDisable(!hayVehiculoSeleccionado);
        descripcionMantenimientoField.setDisable(!hayVehiculoSeleccionado);
        costeMantenimientoField.setDisable(!hayVehiculoSeleccionado);
        kmMantenimientoField.setDisable(!hayVehiculoSeleccionado);
        guardarMantenimientoButton.setDisable(!hayVehiculoSeleccionado);
        cancelarMantenimientoButton.setDisable(!hayVehiculoSeleccionado);
        mantenimientosList.setDisable(!hayVehiculoSeleccionado);
        eliminarMantenimientoButton.setDisable(true);
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
        mantenimientosList.getItems().clear();
        limpiarFormularioMantenimiento();
        actualizarResumenMantenimientos();
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
        alerta.setContentText(seleccionado + "\nTambién se eliminarán sus mantenimientos.");

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
            String descripcion = descripcionMantenimientoField.getText().trim();
            double coste = Double.parseDouble(costeMantenimientoField.getText());
            int kmEnLaRevision = Integer.parseInt(kmMantenimientoField.getText());

            String errorFecha = Validacion.validarFechaMantenimiento(fechaRevision);
            if (!errorFecha.isEmpty()) {
                resultadoMantenimientoLabel.setText(errorFecha);
                return;
            }

            String errorDescripcion = Validacion.validarDescripcionMantenimiento(descripcion);
            if (!errorDescripcion.isEmpty()) {
                resultadoMantenimientoLabel.setText(errorDescripcion);
                return;
            }

            String errorCoste = Validacion.validarCosteMantenimiento(coste);
            if (!errorCoste.isEmpty()) {
                resultadoMantenimientoLabel.setText(errorCoste);
                return;
            }

            String errorKm = Validacion.validarKmMantenimiento(kmEnLaRevision, seleccionado.getKilometraje());
            if (!errorKm.isEmpty()) {
                resultadoMantenimientoLabel.setText(errorKm);
                return;
            }

            Mantenimiento mantenimiento = new Mantenimiento(
                    seleccionado.getMatricula(),
                    fechaRevision,
                    descripcion,
                    coste,
                    kmEnLaRevision);

            Mantenimiento mantenimientoSeleccionado = mantenimientosList.getSelectionModel().getSelectedItem();
            if (mantenimientoSeleccionado != null) {
                mantenimiento.setId(mantenimientoSeleccionado.getId());
                boolean actualizado = mantenimientoService.actualizarMantenimiento(mantenimiento);
                if (!actualizado) {
                    resultadoMantenimientoLabel.setText("No se pudo actualizar el mantenimiento");
                    return;
                }
                resultadoMantenimientoLabel.setText("Mantenimiento actualizado");
            } else {
                boolean guardado = mantenimientoService.guardarMantenimiento(mantenimiento);
                if (!guardado) {
                    resultadoMantenimientoLabel.setText("No se pudo guardar el mantenimiento");
                    return;
                }
                resultadoMantenimientoLabel.setText("Mantenimiento guardado");
            }

            refrescarMantenimientos(seleccionado.getMatricula());
            limpiarFormularioMantenimiento();
            prepararNuevoMantenimiento();

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
        mantenimientosList.getSelectionModel().clearSelection();
        guardarMantenimientoButton.setText("Guardar mant.");
    }

    private void prepararNuevoMantenimiento() {
        if (vehiculosList.getSelectionModel().getSelectedItem() != null) {
            fechaMantenimientoField.setText(LocalDate.now().toString());
        }
    }

    @FXML
    private void cancelarEdicionMantenimiento() {
        limpiarFormularioMantenimiento();
        prepararNuevoMantenimiento();
        resultadoMantenimientoLabel.setText("");
    }

    @FXML
    private void eliminarMantenimiento() {
        Vehiculo vehiculoSeleccionado = vehiculosList.getSelectionModel().getSelectedItem();
        Mantenimiento mantenimientoSeleccionado = mantenimientosList.getSelectionModel().getSelectedItem();

        if (vehiculoSeleccionado == null) {
            resultadoMantenimientoLabel.setText("Selecciona un vehículo primero");
            return;
        }

        if (mantenimientoSeleccionado == null) {
            resultadoMantenimientoLabel.setText("Selecciona un mantenimiento para eliminar");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminación");
        alerta.setHeaderText("¿Eliminar este mantenimiento?");
        alerta.setContentText(mantenimientoSeleccionado.toString());

        Optional<ButtonType> respuesta = alerta.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            boolean eliminado = mantenimientoService.eliminarMantenimiento(mantenimientoSeleccionado.getId());
            if (eliminado) {
                resultadoMantenimientoLabel.setText("Mantenimiento eliminado");
                refrescarMantenimientos(vehiculoSeleccionado.getMatricula());
                limpiarFormularioMantenimiento();
                prepararNuevoMantenimiento();
            } else {
                resultadoMantenimientoLabel.setText("No se pudo eliminar el mantenimiento");
            }
        }
    }
}
