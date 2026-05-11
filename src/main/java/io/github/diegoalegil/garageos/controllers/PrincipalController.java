package io.github.diegoalegil.garageos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrincipalController {

    @FXML
    private Label saludoLabel;

    @FXML
    private void cambiarSaludo() {
        saludoLabel.setText("¡Has pulsado el botón!");
    }
}