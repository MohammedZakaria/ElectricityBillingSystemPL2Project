package org.pl.electricitybillingsystempl2project.Controllers;


import java.awt.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.pl.electricitybillingsystempl2project.entities.Admin;
import org.pl.electricitybillingsystempl2project.entities.Customer;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManager;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManagerFactory;

import java.io.File;

public class AddNewCustomer {
    @FXML
    protected void cancelCustomer() {
        ControllersUtils.closePageWithNode(addNewCustName);
        ControllersUtils.openPage( "login.fxml" , "Login");
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
        ControllersUtils.openPage( "customer-details.fxml" , "Customer Detail");
        ControllersUtils.closePageWithNode(addNewCustName);

    }


    @FXML
    protected void uploadContract() {
        JFileChooser Contract = new JFileChooser();
        File f = Contract.getSelectedFile();

    }
}
