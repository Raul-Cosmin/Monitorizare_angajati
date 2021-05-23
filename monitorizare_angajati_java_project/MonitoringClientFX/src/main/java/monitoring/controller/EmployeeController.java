package monitoring.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monitoring.domain.*;
import monitoring.domain.exception.MonitoringException;
import monitoring.service.IMonitoringEmployeeObserver;
import monitoring.service.IMonitoringServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController extends UnicastRemoteObject implements IMonitoringEmployeeObserver, Serializable {

    @FXML
    private AnchorPane anchor_tasks;
    @FXML
    private AnchorPane anchor_viewTeams;
    private Stage crtStage;

    private Employee employee;

    private IMonitoringServices service;

    @FXML
    private Label employeeName_lbl;

    @FXML
    private TableView<TaskDTO> tableReceivedTasks;

    @FXML
    private TableColumn<TaskDTO, Integer> idTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> descrptionTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> assignDateTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> completionDateTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> deadlineTaskCol;

    @FXML
    private TableView<TeamDTO> tableTeam;

    @FXML
    private TableColumn<TeamDTO, Integer> idTeamCol;

    @FXML
    private TableColumn<TeamDTO, String> teamNameCol;

    @FXML
    private TableColumn<TeamDTO, MemberRole> rolNameCol;

    private ObservableList<TeamDTO> modelViewTeam =  FXCollections.observableArrayList();


    @FXML
    private TableColumn<TaskDTO, String> statusTaskCol;



    @FXML
    private JFXComboBox<String> comboStatus;


    private ObservableList<TaskDTO> modelViewTaskReceived =  FXCollections.observableArrayList();

    private Presence presence;


    public EmployeeController() throws RemoteException {
        super();
    }

    public void setService(IMonitoringServices service){
        this.service = service;

    }
    public void logInEmployee(Employee employeeCrt) {
        this.presence = new Presence();

        presence.setArrivalTime(LocalDateTime.now());

        this.employee = service.employee_logIn(employeeCrt,presence,this);

        this.presence.setIdEmployee(this.employee.getId());

        this.employeeName_lbl.setText(this.employee.getLastName() + "  " + this.employee.getFirstName());
    }

    public void setCrtStage(Stage stage) {
        this.crtStage = stage;
        initialize();

        comboStatus.getItems().add("AVAILABLE");
        comboStatus.getItems().add("IN_PAUSE");
        comboStatus.getItems().add("BUSY");

        comboStatus.setPromptText("AVAILABLE");

        initModelTasks(getDTOFromTasks(service.getAllTasksForEmployee(employee.getId())));


    }

    public void handleClickOnLogOut(ActionEvent actionEvent) {

        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

//         employeeStage.close();
//         this.logInStage.close();

        System.exit(0);
    }
    public void logout() {
        try {
            this.employee.setEmployeeStatus(EmployeeStatus.OFFLINE);

            this.presence.setDepartureTime(LocalDateTime.now());

            System.out.println("[CONTROLLER EMPLOYER] Try to close application" +employee);

            service.employee_logOut(employee,presence, this);
        } catch (Exception e) {
            System.out.println("Logout error " + e);
        }
    }


    private void teamTableInitialize(){
        idTeamCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamNameCol.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        rolNameCol.setCellValueFactory(new PropertyValueFactory<>("memberRole"));

        tableTeam.setItems(modelViewTeam);
    }


    private void tasksTableInitialize(){
        idTaskCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descrptionTaskCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        assignDateTaskCol.setCellValueFactory(new PropertyValueFactory<>("assignDate_str"));
        completionDateTaskCol.setCellValueFactory(new PropertyValueFactory<>("completionDate_str"));
        deadlineTaskCol.setCellValueFactory(new PropertyValueFactory<>("deadline_str"));
        statusTaskCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableReceivedTasks.setItems(modelViewTaskReceived);

    }

    private void initModelTasks(List<TaskDTO> tasks){
        modelViewTaskReceived.setAll(tasks);
    }

    public void initialize() {

        anchor_tasks.toFront();
        tasksTableInitialize();
        teamTableInitialize();


    }

    private List<TaskDTO> getDTOFromTasks(List<Task> tasks) {
        List<TaskDTO> lst = new ArrayList<>();

        for(Task t : tasks){
            TaskDTO taskDTO = new TaskDTO(t.getId(), t.getDescription(),t.getStatus(),t.getIdEmployee(),t.getIdTeam());
            taskDTO.setCompletionDate_str(t.getCompletionDate());
            taskDTO.setAssignDate_str(t.getAssignDate());
            taskDTO.setDeadline_str(t.getDeadline());

            lst.add(taskDTO);
        }

        return lst;
    }

    public void handleClickOnViewTasks(ActionEvent actionEvent) {
        initModelTasks(getDTOFromTasks(service.getAllTasksForEmployee(employee.getId())));
        anchor_tasks.toFront();
    }

    private void initModelTeams(List<TeamDTO> teams){
        modelViewTeam.setAll(teams);
    }

    public void handleClickOnViewTeams(ActionEvent actionEvent) {



        initModelTeams(service.getAllTeamsForEmployee(employee.getId()));

        anchor_viewTeams.toFront();
    }

    @FXML
    void handleResolveTask(ActionEvent event) {
        TaskDTO selectedTask = tableReceivedTasks.getSelectionModel().getSelectedItem();
        if(selectedTask == null ){
            MessageAlert.showErrorMessage("Please select a task!");
            return;
        }
        try{
            if(selectedTask.getStatus() == TaskStatus.IN_PROGRESS)
                throw new MonitoringException("This task is already in progress");

            if(selectedTask.getStatus() ==  TaskStatus.FINISHED)
                throw new MonitoringException("This task is finished!");


            Task t = new Task(
                    selectedTask.getId(),
                    selectedTask.getDescription(),
                    selectedTask.getDateAssign(),
                    selectedTask.getDateDeadline(),
                    selectedTask.getDateCompletion(),
                    selectedTask.getStatus());

            t.setIdEmployee(this.employee.getId());

            service.resolveTask(t);

            initModelTasks(getDTOFromTasks(service.getAllTasksForEmployee(employee.getId())));

            MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Task status is IN_PROGRESS!");
        }
        catch (Exception ex){
            MessageAlert.showErrorMessage("Error: " + ex);
        }

    }

    @FXML
    void handleFinishTask(ActionEvent event) {
        TaskDTO selectedTask = tableReceivedTasks.getSelectionModel().getSelectedItem();
        if(selectedTask == null ){
            MessageAlert.showErrorMessage("Please select a task!");
            return;
        }



        try{

            if(selectedTask.getStatus() == TaskStatus.FINISHED)
                throw new MonitoringException("This task is already finished!");

            Task t = new Task(
                    selectedTask.getId(),
                    selectedTask.getDescription(),
                    selectedTask.getDateAssign(),
                    selectedTask.getDateDeadline(),
                    selectedTask.getDateCompletion(),
                    selectedTask.getStatus());

            t.setIdEmployee(this.employee.getId());

            employee.setNrTasksCompleted(employee.getNrTasksCompleted() + 1);

            service.finishTask(t,employee);

            initModelTasks(getDTOFromTasks(service.getAllTasksForEmployee(employee.getId())));

            MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Task status is FINISHED!");
        }
        catch (Exception ex){
            MessageAlert.showErrorMessage("Error: " + ex);
        }

    }

    @FXML
    void handleChangeStatus(ActionEvent event) {
        String statusValue = comboStatus.getValue();

        EmployeeStatus status = null;

        if(statusValue != null) {
            switch (statusValue) {
                case "AVAILABLE" -> status = EmployeeStatus.AVAILABLE;
                case "IN_PAUSE" -> status = EmployeeStatus.IN_PAUSE;
                case "BUSY" -> status = EmployeeStatus.BUSY;
            }

            this.employee.setEmployeeStatus(status);

            service.changeEmployeeStatus(employee);

        }
    }


    @Override
    public void notifyNewTeamIsCreated(List<TeamDTO> teamList) {
        Platform.runLater(()->{
            initModelTeams(teamList);
        });
    }

    @Override
    public void notifyNewTaskIsAssigned(List<Task> taskList) {
        Platform.runLater(()->{
            initModelTasks(getDTOFromTasks(taskList));
        });
    }
}
