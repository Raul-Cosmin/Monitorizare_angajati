package monitorizare.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monitorizare.domain.*;
import monitorizare.observer.Observer;
import monitorizare.service.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployerController implements Initializable, Observer {


    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, Integer> idTaskCol;

    @FXML
    private TableColumn<Task, String> descriptionTaskCol;

    @FXML
    private TableColumn<Task, String> assignDateTaskCol;

    @FXML
    private TableColumn<Task, String> completionDateTaskCol;

    @FXML
    private TableColumn<Task, String> deadlineTaskCol;

    @FXML
    private TableColumn<Task, TaskStatus> statusTaskCol;
    @FXML
    private TableColumn<Task, Integer> idEmployeeTaskCol;

    @FXML
    private TableColumn<Task, Integer> idTeamTaskCol;

    private ObservableList<Task> modelViewTask =  FXCollections.observableArrayList();



    @FXML
    private TextField teamName_txt;
    @FXML
    private JFXComboBox<String> comboMemberRole;
    @FXML
    private TextField employeeSelected_txt;

    private Employer crtEmployer;

    private EmployeeDTO selectedEmp;


    @FXML
    private TableView<EmployeeDTOTeam> teamTable;

    @FXML
    private TableColumn<EmployeeDTOTeam, Integer> idEmployeeTeamCol;

    @FXML
    private TableColumn<EmployeeDTOTeam, String> lastNameEmpTeamCol;

    @FXML
    private TableColumn<EmployeeDTOTeam, String> firstNameEmpTeamCol;

    @FXML
    private TableColumn<EmployeeDTOTeam, MemberRole> memberRoleTeamCol;

    private ObservableList<EmployeeDTOTeam> modelViewEmployeesTeam= FXCollections.observableArrayList();


    private List<EmployeeDTOTeam> empTeamsList = new ArrayList<>();


    @FXML
    private TableView<Team> teamsTable;

    @FXML
    private TableColumn<Team, Integer> idTeamCol;

    @FXML
    private TableColumn<Team, String> teamNameCol;

    @FXML
    private TableColumn<Team, Integer> noMembersTeamCol;

    private ObservableList<Team> modelViewTeams = FXCollections.observableArrayList();




    @FXML
    private Label employerName_lbl;
    @FXML
    private AnchorPane anchor_tasks;
    @FXML
    private AnchorPane anchor_topEmployees;
    @FXML
    private AnchorPane anchor_createTeam;
    @FXML
    private TableView<EmployeeDTO> employeesTable;
    @FXML
    private TableColumn<EmployeeDTO,Integer> idEmployeeCol;
    @FXML
    private TableColumn<EmployeeDTO,String> lastNameEmployeeCol;
    @FXML
    private TableColumn<EmployeeDTO,String> firstNameEmployeeCol;
    @FXML
    private TableColumn<EmployeeDTO,String> arrivalTimeEmployeeCol;
    @FXML
    private TableColumn<EmployeeDTO, String> departureTimeEmployeeCol;
    @FXML
    private TableColumn<EmployeeDTO, EmployeeStatus> employeeStatusCol;

    private ObservableList<EmployeeDTO> modelViewEmployees= FXCollections.observableArrayList();

    private Stage crtStage;

    private Service service;

    public void setService(Service service){
        this.service = service;

        this.service.addObserver(this);
        initModelEmployees(service.getAllEmployeesForTodayPresence());
        initModelTasks(service.getAllTasks());

        initModelTeams(service.getAllTeams());

    }

    private void initModelTeams(List<Team> teams){
        modelViewTeams.setAll(teams);

    }

    private void initModelTasks(List<Task> tasks){
        modelViewTask.setAll(tasks);

    }

    private void initModelEmployees(List<EmployeeDTO> allEmployeesForTodayPresence) {
        modelViewEmployees.setAll(allEmployeesForTodayPresence);

    }

    private void initModelEmpTeams(List<EmployeeDTOTeam> empTeams){
        modelViewEmployeesTeam.setAll(empTeams);

    }

    public void handleClickOnCreateTeam(ActionEvent actionEvent) {
        anchor_createTeam.toFront();

        employeeSelected_txt.setText("");

        empTeamsList.clear();
        initModelEmpTeams(empTeamsList);
    }

    public void handleClickOnViewTop(ActionEvent actionEvent) {
        anchor_topEmployees.toFront();
    }


    public void handleClickOnAssignTask(ActionEvent actionEvent) {
        anchor_tasks.toFront();
        initModelTeams(service.getAllTeams());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchor_tasks.toFront();
        employeesTableInitialize();

        employeesTeamTableInitialize();

        tasksTableInitialize();

        teamTableInitialize();



        comboMemberRole.getItems().add("TEAM_LEADER");
        comboMemberRole.getItems().add("FACILITATOR");
        comboMemberRole.getItems().add("RECORDER");
        comboMemberRole.getItems().add("TIME_KEEPER");
        comboMemberRole.getItems().add("MEMBER");

        comboMemberRole.setPromptText("Member role");

    }

    private void teamTableInitialize() {
        idTeamCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamNameCol.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        noMembersTeamCol.setCellValueFactory(new PropertyValueFactory<>("noMembers"));

        teamsTable.setItems(modelViewTeams);
    }

    private void tasksTableInitialize(){
        idTaskCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTaskCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        assignDateTaskCol.setCellValueFactory(new PropertyValueFactory<>("assignDate_str"));
        completionDateTaskCol.setCellValueFactory(new PropertyValueFactory<>("completionDate_str"));
        deadlineTaskCol.setCellValueFactory(new PropertyValueFactory<>("deadline_str"));
        statusTaskCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        idEmployeeTaskCol.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));
        idTeamTaskCol.setCellValueFactory(new PropertyValueFactory<>("idTeam"));

        tasksTable.setItems(modelViewTask);

    }

    private void employeesTeamTableInitialize() {
        idEmployeeTeamCol.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));
        firstNameEmpTeamCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameEmpTeamCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        memberRoleTeamCol.setCellValueFactory(new PropertyValueFactory<>("memberRole"));

        teamTable.setItems(modelViewEmployeesTeam);

    }

    private void employeesTableInitialize(){
        idEmployeeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameEmployeeCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameEmployeeCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        arrivalTimeEmployeeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        departureTimeEmployeeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        employeeStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        employeesTable.setItems(modelViewEmployees);


    }

    public void handleClickOnLogOut(ActionEvent actionEvent) {
        crtStage.close();
    }

    public void setStage(Stage stage) {
        this.crtStage = stage;
    }

    public void setCurrentEmployer(Employer employer) {
        this.crtEmployer = employer;
        this.employerName_lbl.setText(employer.getLastName() + " " + employer.getFirstName());
    }

    public void handleOnSelectEmployee(ActionEvent event) {
        EmployeeDTO employeeDTO = employeesTable.getSelectionModel().getSelectedItem();

        if(employeeDTO != null){
            this.selectedEmp = employeeDTO;
            employeeSelected_txt.setText("ID="+employeeDTO.getId() + ";Name="+ employeeDTO.getLastName() + " " + employeeDTO.getFirstName());
        }else
            MessageAlert.showErrorMessage("Please select an employee from table!");
    }

    public void handleOnMoveIntoEmpTable(ActionEvent event) {

        if(employeeSelected_txt.getText().equals(""))
        {
            MessageAlert.showErrorMessage("Please select an employee from table!");
            return;

        }

        String memberRole_str = comboMemberRole.getValue();

        MemberRole memberRole = null;

        if(memberRole_str != null)
            switch (memberRole_str) {
                case "TEAM_LEADER" -> memberRole = MemberRole.TEAM_LEADER;
                case "FACILITATOR" -> memberRole = MemberRole.FACILITATOR;
                case "RECORDER" -> memberRole = MemberRole.RECORDER;
                case "TIME_KEEPER" -> memberRole = MemberRole.TIME_KEEPER;
                case "MEMBER" -> memberRole = MemberRole.MEMBER;
            }

        if(memberRole == null)
        {
            MessageAlert.showErrorMessage("Please choose a member role for employee!");
            return;
        }

        EmployeeDTOTeam employeeDTOTeam = new EmployeeDTOTeam(this.selectedEmp.getId(),this.selectedEmp.getFirstName(),this.selectedEmp.getLastName(),memberRole);

        for(EmployeeDTOTeam emp : empTeamsList)
            if(emp.getIdEmployee() == employeeDTOTeam.getIdEmployee())
            {
                MessageAlert.showErrorMessage("This employee is already selected!");
                return;
            }


        empTeamsList.add(employeeDTOTeam);
        initModelEmpTeams(empTeamsList);


    }

    public void handleOnMoveOutEmpTable(ActionEvent event) {
        EmployeeDTOTeam selectedItem = teamTable.getSelectionModel().getSelectedItem();

        if(selectedItem == null){
            MessageAlert.showErrorMessage("Select an employee from team table!");
            return;
        }

        empTeamsList.removeIf(e -> e.getIdEmployee() == selectedItem.getIdEmployee());
        initModelEmpTeams(empTeamsList);


    }

    public void handOnTeamCreated(ActionEvent event) {
        if(empTeamsList.size() < 2)
        {
            MessageAlert.showErrorMessage("In team must be at least 2 members!");
            return;
        }

        String teamName = teamName_txt.getText();

        if(teamName.equals(""))
        {
            MessageAlert.showErrorMessage("Choose a name for team!");
            return;
        }

        try{
            service.createNewTeam(teamName,empTeamsList);

        }catch (Exception ex){
            MessageAlert.showErrorMessage(ex.getMessage());
            return;
        }

        MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Team is created!");

        empTeamsList.clear();
        initModelEmpTeams(empTeamsList);

        teamName_txt.setText("");
        employeeSelected_txt.setText("");

    }

    public void handleOnSendTaskToEmployee(ActionEvent event) {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        EmployeeDTO employeeDTO = employeesTable.getSelectionModel().getSelectedItem();

        if(selectedTask == null || employeeDTO == null){
            MessageAlert.showErrorMessage("Please select an employee and a task for assign task operation!");
            return;
        }

        if(selectedTask.getIdEmployee() != null || selectedTask.getIdTeam() != null)
        {
            MessageAlert.showErrorMessage("This task is already assigned!");
            return;
        }

        try{
            selectedTask.setIdEmployee(employeeDTO.getId());
            service.assignTask(selectedTask);
            MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Task is assigned for employee!");
        }
        catch (Exception ex){
            MessageAlert.showErrorMessage("Error: " + ex);
        }

    }

    public void handleOnSendTaskToTeam(ActionEvent event) {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        Team team = teamsTable.getSelectionModel().getSelectedItem();





        if(selectedTask == null || team == null){
            MessageAlert.showErrorMessage("Please select a team and a task for assign task operation!");
            return;
        }

        if(selectedTask.getIdEmployee() != null || selectedTask.getIdTeam() != null)
        {
            MessageAlert.showErrorMessage("This task is already assigned!");
            return;
        }

        try{
            selectedTask.setIdTeam(team.getId());
            service.assignTask(selectedTask);
            MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Task is assigned for team!");
        }
        catch (Exception ex){
            MessageAlert.showErrorMessage("Error: " + ex);
        }
    }

    @Override
    public void update() {
        initModelTasks(service.getAllTasks());
    }
}
