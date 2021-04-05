package monitorizare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployerController implements Initializable {

    public AnchorPane anchor_tasks;
    public AnchorPane anchor_topEmployees;
    public AnchorPane anchor_createTeam;

    private Stage crtStage;

    public void handleClickOnCreateTeam(ActionEvent actionEvent) {
        anchor_createTeam.toFront();
    }

    public void handleClickOnViewTop(ActionEvent actionEvent) {
        anchor_topEmployees.toFront();
    }


    public void handleClickOnAssignTask(ActionEvent actionEvent) {
        anchor_tasks.toFront();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchor_tasks.toFront();

    }

    public void handleClickOnLogOut(ActionEvent actionEvent) {
        crtStage.close();
    }

    public void setStage(Stage stage) {
        this.crtStage = stage;
    }
}
