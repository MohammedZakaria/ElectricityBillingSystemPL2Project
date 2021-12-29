package org.pl.electricitybillingsystempl2project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.pl.electricitybillingsystempl2project.entities.Operator;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManager;
import org.pl.electricitybillingsystempl2project.entitymanager.EntityManagerFactory;

public class AddNewOperator {
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField confirm_passwordTxt;

    public void registerOperator() {
String name =nameTxt.getText();
String phone=phoneTxt.getText();
String password=passwordTxt.getText();
String confirmPassword=confirm_passwordTxt.getText();
        Operator op =new Operator();
        op.setName(name);
        op.setPhone(phone);
        op.setPassword(password);
        if(op.getPassword()==confirmPassword){
            EntityManagerFactory.getEntityManager(Operator.class).save(op)
                    .onFailure(t->   new Alert(Alert.AlertType.ERROR, "Not save Operator").show());
        }else
            new Alert(Alert.AlertType.ERROR, "Confirm Password should be like password ").show();
        ControllersUtils.openPage("operator.fxml","operator");
        ControllersUtils.closePageWithNode(nameTxt);
    }

    @FXML
    protected void cancelOperator() {
        ControllersUtils.closePageWithNode(passwordTxt);
    }
}
