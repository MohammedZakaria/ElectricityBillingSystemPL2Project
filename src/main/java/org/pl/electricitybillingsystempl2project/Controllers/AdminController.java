package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminController {
    @FXML
    private TextField adminCustSearchById;
    @FXML
    protected void Exit() {
        ControllersUtils.openPage("login.fxml","login");
        ControllersUtils.closePageWithNode(adminCustSearchById);
    }

    @FXML
    protected void adminCustomersSearch() {

    }

    @FXML
    protected void adminAddCustomer() {

    }

    @FXML
    protected void adminEditCustomer() {

    }

    @FXML
    protected void adminUsersSearch() {

    }

    @FXML
    protected void adminAddNewUser() {

    }

    @FXML
    protected void adminEditUser() {

    }

}
