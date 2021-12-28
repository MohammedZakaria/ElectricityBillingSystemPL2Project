package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.pl.electricitybillingsystempl2project.HelloApplication;

import java.io.IOException;

public class ControllersUtils {

    public static void closePageWithNode(Node node) {
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }


    public static void openPage(String fxmlPath, String title) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "an error occurred while opening the window").show();
        }
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
