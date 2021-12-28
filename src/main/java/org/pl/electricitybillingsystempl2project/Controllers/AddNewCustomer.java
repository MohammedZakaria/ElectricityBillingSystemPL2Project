package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.pl.electricitybillingsystempl2project.entities.Admin;
import org.pl.electricitybillingsystempl2project.entities.Customer;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManager;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManagerFactory;

public class AddNewCustomer {
    @FXML
    protected void cancelCustomer() {

    }

    @FXML
    protected void registerCustomer() {
       Customer customer = new Customer();
        String name = addNewCustName.getText();
        String  email = addNewCustEmail.getText();
        String password = addNewCustPassword.getText();
        String Confirm_Password = addNewCustConfirmPassword.getText();
        String Phone = addNewCustPhone.getText();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setPhone(Phone);

        EntityManagerFactory.getEntityManager(Customer.class).save(customer)
                .onFailure(t ->new Alert(Alert.AlertType.ERROR, "Something went Wrong").show());

    }

    @FXML
    protected void uploadContract() {

    }

    @FXML
    protected void uploadPhoto() {

    }
}
