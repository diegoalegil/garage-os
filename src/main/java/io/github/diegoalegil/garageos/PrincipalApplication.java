package io.github.diegoalegil.garageos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                PrincipalApplication.class.getResource("principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        scene.getStylesheets().add(PrincipalApplication.class.getResource("styles.css").toExternalForm());

        stage.setTitle("GarageOS");
        stage.getIcons().add(new Image(PrincipalApplication.class.getResourceAsStream("assets/app-icon.png")));
        stage.setMinWidth(1000);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
