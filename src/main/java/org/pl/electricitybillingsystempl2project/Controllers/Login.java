package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.pl.electricitybillingsystempl2project.HelloApplication;
import org.pl.electricitybillingsystempl2project.entities.Admin;
import org.pl.electricitybillingsystempl2project.entities.Customer;
import org.pl.electricitybillingsystempl2project.entities.User;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManager;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManagerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Login {


    @FXML
    private ToggleGroup loginAs;
    @FXML
    private TextField loginEmailTxt;
    @FXML
    private PasswordField loginPasswordTxt;


    @FXML
    protected void register() {

    }

    @FXML
    protected void login() {
        String userType = ((RadioButton) loginAs.getSelectedToggle()).getText();
        String email = loginEmailTxt.getText();
        String password = loginPasswordTxt.getText();
        switch (userType.toLowerCase()) {
            case "admin" ->
                    doUserLogin(email, password, userType, EntityManagerFacto                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ry.getEntityManager(Admin.class));
            case "operator" ->
                    doUserLogin(email, password, userType, EntityManagerFactory.getEntityManager(Operator.class));
            case "customer" ->
                    doUserLogin(email, password, userType, EntityManagerFactory.getEntityManager(Customer.class));
        }
    }

    private void doUserLogin(String email, String password, String userType, EntityManager em) {
        em.searchByKeyValue("email", email)
                .onSuccess(data -> {
                    List<User> users = (List<User>) data;
                    if (users.size() == 1)
                        if (Objects.equals(users.get(0).getPassword(), password)) {
                            goToUserPage(userType);
                            Stage currStage = (Stage) loginPasswordTxt.getScene().getWindow();
                            currStage.close();
                            return;
                        }
                    new Alert(Alert.AlertType.ERROR, "username or password is invalid").show();
                }).onFailure(throwable -> {
                    ((Exception) throwable).printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "something went wrong").show();
                }).get()
        ;
    }

    private void goToUserPage(String userType) {
        switch (userType) {
            case "admin" ->
                    openPage("admin.fxml", "admin");
            case "operator" ->
                    openPage("operator.fxml", "operator");
            case "customer" ->
                    openPage("customer-details.fxml", "customer");

        }
    }

    private void openPage(String fxmlPath, String title) {
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
