package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomerDetails {
    @FXML private Label custDetailsId;
    @FXML
    protected void customerDetailsPrintBill() {
    }

    @FXML
    protected void customerDetailsPayBill() {

    }

    @FXML
    protected void customerDetailsEnterReading() {

    }

    @FXML
    protected void customerDetailsSendComplain() {

    }

    @FXML
    protected void customerDetailsDownloadContract() {

    }

    @FXML
    protected void customerDetailsCancel() {
        ControllersUtils.openPage( "login.fxml" , "Login");
        ControllersUtils.closePageWithNode(custDetailsId);
    }
}
