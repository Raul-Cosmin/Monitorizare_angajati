package monitorizare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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


    private Stage logInStage;

    public void handleClickOnLogInEmployer(ActionEvent actionEvent) {

        Stage stage1 = new Stage();
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/employerGUI.fxml"));
        try {
            Parent root=loader.load();

            Scene scene = new Scene(root);
            EmployerController controller = loader.getController();
            controller.setStage(stage1);



            stage1.setScene(scene);

            stage1.show();

            this.logInStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClickOnLogInEmployee(ActionEvent actionEvent) {
        Stage stage1 = new Stage();
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/employeeGUI.fxml"));
        try {
            Parent root=loader.load();

            EmployeeController controller = loader.getController();
            controller.setStage(stage1);


            Scene scene = new Scene(root);

            stage1.setScene(scene);

            stage1.show();

            this.logInStage.close();

        } catch (IOException e) {
            e.printStackTrace();
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

    public void setLogInStage(Stage logInStage) {
        this.logInStage = logInStage;

    }
}
