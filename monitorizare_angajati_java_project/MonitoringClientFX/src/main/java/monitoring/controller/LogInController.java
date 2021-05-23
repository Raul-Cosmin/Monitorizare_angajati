package monitoring.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monitoring.domain.Employee;
import monitoring.domain.Employer;
import monitoring.domain.Presence;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private AnchorPane logInEmployer_anchor;
    @FXML
    private TextField emailEmployer_textField;
    @FXML
    private TextField passwordEmployer_textField;
    @FXML
    private AnchorPane logInEmployee_anchor;
    @FXML
    private TextField emailEmployee_textField;
    @FXML
    private TextField passwordEmployee_textField;
    @FXML
    private AnchorPane logIn_anchor;


    private Stage myStage;

    //employer
    private Parent employerParent;
    private EmployerController employerController;

    //employee
    private Parent employeeParent;
    private EmployeeController employeeController;

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setEmployerStage(Parent employerParent) {
        this.employerParent = employerParent;
    }

    public void setEmployerController(EmployerController employeeController) {
        this.employerController = employeeController;
    }


    public void handleClickOnLogInEmployer(ActionEvent actionEvent) {

        String emailEmployer = emailEmployer_textField.getText();
        String passwordEmployer = passwordEmployer_textField.getText();

        if(emailEmployer.equals("") || passwordEmployer.equals(""))
        {
            MessageAlert.showErrorMessage("Email and password must be not empty!");
            return;
        }

        try{

            Employer employer = new Employer(emailEmployer,passwordEmployer);

            employerController.logInEmployer(employer);

            Stage stage1 = new Stage();
            stage1.setScene(new Scene(employerParent));
            stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    employerController.logout();
                    System.exit(0);
                }
            });

            employerController.setCrtStage(stage1);
            employerController.setLogInStage(this.myStage);

            stage1.show();
            this.myStage.hide();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


        }
        catch (Exception ex){
            MessageAlert.showErrorMessage(ex.getMessage());
        }

    }

    public void handleClickOnLogInEmployee(ActionEvent actionEvent) {

        String emailEmployee = emailEmployee_textField.getText();
        String passwordEmployee = passwordEmployee_textField.getText();

        if(emailEmployee.equals("") || passwordEmployee.equals(""))
        {
            MessageAlert.showErrorMessage("Email and password must be not empty!");
            return;
        }

        try{

            Employee employee = new Employee(emailEmployee,passwordEmployee);

            employeeController.logInEmployee(employee);

            Stage stage2= new Stage();
            stage2.setScene(new Scene(employeeParent));
            stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    employeeController.logout();
                    System.exit(0);
                }
            });

            employeeController.setCrtStage(stage2);

            stage2.show();
            this.myStage.hide();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


        }
        catch (Exception ex){
            MessageAlert.showErrorMessage(ex.getMessage());
        }

    }

    public void handleClickOnGoToLogInEmployer(ActionEvent actionEvent) {
        logInEmployer_anchor.toFront();
    }

    public void handleClickOnGoToLogInEmployee(ActionEvent actionEvent) {
        logInEmployee_anchor.toFront();
    }

    public void handleClickOnBack(ActionEvent actionEvent) {
        logIn_anchor.toFront();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logIn_anchor.toFront();
    }


    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setEmployeeStage(Parent employeeParent) {
        this.employeeParent = employeeParent;
    }
}
