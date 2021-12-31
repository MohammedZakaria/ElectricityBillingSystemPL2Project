package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.pl.electricitybillingsystempl2project.entities.Admin;
import org.pl.electricitybillingsystempl2project.entities.Customer;
import org.pl.electricitybillingsystempl2project.entities.Operator;
import org.pl.electricitybillingsystempl2project.entities.User;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManager;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManagerFactory;

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
        ControllersUtils.openPage("add-new-customer.fxml","customer");
        ControllersUtils.closePageWithNode(loginEmailTxt);
    }

    @FXML
    protected void login() {
        String userType = ((RadioButton) loginAs.getSelectedToggle()).getText();
        String email = loginEmailTxt.getText();
        String password = loginPasswordTxt.getText();
        switch (userType.toLowerCase()) {
            case "admin" ->
                    doUserLogin(email, password, userType, EntityManagerFactory.getEntityManager(Admin.class));
            case "operator" ->
                    doUserLogin(email, password, userType, EntityManagerFactory.getEntityManager(Operator.class));
            case "customer" ->
                    doUserLogin(email, password, userType, EntityManagerFactory.getEntityManager(Customer.class));
        }
    }

    private <T extends User> void doUserLogin(String email, String password, String userType, EntityManager<T> em) {
//        goToUserPage(userType);
//        ControllersUtils.closePageWithNode(loginPasswordTxt);
        em.searchByKeyValue("email", email)
                .onSuccess(users -> {
                    if (users.size() == 1)
                        if (Objects.equals(users.get(0).getPassword(), password)) {
                            goToUserPage(userType);
                            ControllersUtils.closePageWithNode(loginPasswordTxt);
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
        switch (userType.toLowerCase()) {
            case "admin" ->
                    ControllersUtils.openPage("admin.fxml", "admin");
            case "operator" ->
                    ControllersUtils.openPage("operator.fxml", "operator");
            case "customer" ->
                    ControllersUtils.openPage("customer-details.fxml", "customer");

        }
    }


}
