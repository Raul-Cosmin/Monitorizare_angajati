package monitorizare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {


    @FXML
    private AnchorPane anchor_tasks;
    @FXML
    private AnchorPane anchor_viewTeams;
    private Stage crtStage;


    public void setStage(Stage stage) {
        this.crtStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            anchor_tasks.toFront();
    }

    public void handleClickOnViewTasks(ActionEvent actionEvent) {
        anchor_tasks.toFront();
    }

    public void handleClickOnViewTeams(ActionEvent actionEvent) {
        anchor_viewTeams.toFront();
    }

    public void handleClickOnLogOut(ActionEvent actionEvent) {
        this.crtStage.close();
    }
}
