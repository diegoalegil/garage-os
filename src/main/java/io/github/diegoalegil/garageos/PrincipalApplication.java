package io.github.diegoalegil.garageos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PrincipalApplication extends Application {

    @Override
    public void start(Stage stage) {
        Label saludo = new Label("¡Bienvenido a GarageOS!");
        StackPane root = new StackPane(saludo);
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("GarageOS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
