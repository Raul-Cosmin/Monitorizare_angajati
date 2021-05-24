package monitoring.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monitoring.domain.*;
import monitoring.service.IMonitoringEmployerObserver;
import monitoring.service.IMonitoringObserver;
import monitoring.service.IMonitoringServices;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployerController  extends UnicastRemoteObject implements IMonitoringEmployerObserver, Serializable {


    @FXML
    private TableView<TaskDTO> tasksTable;

    @FXML
    private TableColumn<TaskDTO, Integer> idTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> descriptionTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> assignDateTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> completionDateTaskCol;

    @FXML
    private TableColumn<TaskDTO, String> deadlineTaskCol;

    @FXML
    private TableColumn<TaskDTO, TaskStatus> statusTaskCol;
    @FXML
    private TableColumn<TaskDTO, Integer> idEmployeeTaskCol;

    @FXML
    private TableColumn<TaskDTO, Integer> idTeamTaskCol;

    private ObservableList<TaskDTO> modelViewTask =  FXCollections.observableArrayList();



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


    @FXML
    private TableView<Employee> topEmployees;

    @FXML
    private TableColumn<Employee, Integer> col_idTopEmp;

    @FXML
    private TableColumn<Employee, String> col_lastNameEmpTop;

    @FXML
    private TableColumn<Employee, String> col_firstNameColTop;

    @FXML
    private TableColumn<Employee, String> col_taskSolvedTop;

    private ObservableList<Employee> modelViewTopEmp = FXCollections.observableArrayList();




    private Stage crtStage;
    private Stage logInStage;
    private Employer employer;

    private IMonitoringServices service;


    public EmployerController() throws RemoteException {
        super();
    }

    public void setService(IMonitoringServices service){
        this.service = service;

    }

    public void logInEmployer(Employer employerCrt) {


        this.employer = service.employer_logIn(employerCrt,this);
        this.employerName_lbl.setText(this.employer.getLastName() + "  " + this.employer.getFirstName());
    }

    private List<EmployeeDTO> getDTOFromEmployees(List<Employee> employees){
        List<EmployeeDTO> empsDto = new ArrayList<>();

        for(Employee emp : employees){
            EmployeeDTO e = new EmployeeDTO(emp.getId(),emp.getFirstName(),emp.getLastName(),emp.getEmployeeStatus());
            e.setDepartureTime(emp.getDepartureTime());
            e.setArrivalTime(emp.getArrivalTime());

            empsDto.add(e);

        }

        return empsDto;
    }


    private List<TaskDTO> getDTOFromTasks(List<Task> tasks){
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

    public void setCrtStage(Stage stage1) {
        this.crtStage = stage1;

        initialize();

        comboMemberRole.getItems().add("TEAM_LEADER");
        comboMemberRole.getItems().add("FACILITATOR");
        comboMemberRole.getItems().add("RECORDER");
        comboMemberRole.getItems().add("TIME_KEEPER");
        comboMemberRole.getItems().add("MEMBER");

        comboMemberRole.setPromptText("Member role");


        initModelEmployees(getDTOFromEmployees(service.getAllEmployeesForTodayPresence()));

        initModelTasks(getDTOFromTasks(service.getAllTasks()));

        initModelTeams(service.getAllTeams());

    }

    public void setLogInStage(Stage myStage) {
        this.logInStage = myStage;
    }


    private void initModelTeams(List<Team> teams){
        modelViewTeams.setAll(teams);

    }

    private void initModelTasks(List<TaskDTO> tasks){
        modelViewTask.setAll(tasks);

    }

    private void initModelEmployees(List<EmployeeDTO> allEmployeesForTodayPresence) {
        modelViewEmployees.setAll(allEmployeesForTodayPresence);

    }

    private void initModelTopEmployees(List<Employee> emps){
        modelViewTopEmp.setAll(emps);
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
        initModelTopEmployees(service.getEmployeesForTop());
        anchor_topEmployees.toFront();
    }


    public void handleClickOnAssignTask(ActionEvent actionEvent) {
        anchor_tasks.toFront();
        initModelTeams(service.getAllTeams());
    }

    public void initialize() {
        anchor_tasks.toFront();
        employeesTableInitialize();

        employeesTeamTableInitialize();

        tasksTableInitialize();

        teamTableInitialize();

        topTableInitialize();



    }

    private void topTableInitialize(){
        col_idTopEmp.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstNameColTop.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastNameEmpTop.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_taskSolvedTop.setCellValueFactory(new PropertyValueFactory<>("nrTasksCompleted"));

        topEmployees.setItems(modelViewTopEmp);
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

        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

//         employeeStage.close();
//         this.logInStage.close();

        System.exit(0);
    }
    public void logout() {
        try {

            System.out.println("[CONTROLLER EMPLOYER] Try to close application" +employer);
            service.employer_logOut(employer, this);
        } catch (Exception e) {
            System.out.println("Logout error " + e);
        }
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
        TaskDTO selectedTask = tasksTable.getSelectionModel().getSelectedItem();
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

            Task t = new Task(
                    selectedTask.getId(),
                    selectedTask.getDescription(),
                    selectedTask.getDateAssign(),
                    selectedTask.getDateDeadline(),
                    selectedTask.getDateCompletion(),
                    selectedTask.getStatus());
            t.setIdEmployee(employeeDTO.getId());


            service.assignTask(t);

            initModelTasks(getDTOFromTasks(service.getAllTasks()));

            MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Task is assigned for employee!");
        }
        catch (Exception ex){
            MessageAlert.showErrorMessage("Error: " + ex);
        }

    }

    public void handleOnSendTaskToTeam(ActionEvent event) {
        TaskDTO selectedTask = tasksTable.getSelectionModel().getSelectedItem();
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
            Task t = new Task(
                    selectedTask.getId(),
                    selectedTask.getDescription(),
                    selectedTask.getDateAssign(),
                    selectedTask.getDateDeadline(),
                    selectedTask.getDateCompletion(),
                    selectedTask.getStatus());
            t.setIdTeam(team.getId());

            service.assignTask(t);

            initModelTasks(getDTOFromTasks(service.getAllTasks()));


            MessageAlert.showMessage(Alert.AlertType.INFORMATION,"Task is assigned for team!");
        }
        catch (Exception ex){
            MessageAlert.showErrorMessage("Error: " + ex);
        }
    }




    @Override
    public void notifyEmployeeChangeStatus(List<Employee> employees) throws RemoteException {

        Platform.runLater(()->{
            initModelEmployees(getDTOFromEmployees(employees));
        });

    }

    @Override
    public void notifyChangeTaskStatus(List<Task> tasks) throws RemoteException {
        Platform.runLater(()->{
            initModelTasks(getDTOFromTasks(tasks));
        });
    }
}
