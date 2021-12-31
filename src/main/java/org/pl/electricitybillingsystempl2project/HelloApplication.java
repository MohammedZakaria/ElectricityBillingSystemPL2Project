package org.pl.electricitybillingsystempl2project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pl.electricitybillingsystempl2project.entities.*;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManager;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManagerFactory;
import org.pl.electricitybillingsystempl2project.entitymanager.FilesConfigurations;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FilesConfigurations.initiate();
        FilesConfigurations.register(Admin.class, Operator.class,
                Billing.class, Meter.class, Reading.class);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}