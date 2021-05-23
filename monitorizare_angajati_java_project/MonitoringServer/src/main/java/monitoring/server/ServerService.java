package monitoring.server;

import monitoring.domain.*;
import monitoring.domain.exception.MonitoringException;
import monitoring.service.IMonitoringEmployeeObserver;
import monitoring.service.IMonitoringEmployerObserver;
import monitoring.service.IMonitoringObserver;
import monitoring.service.IMonitoringServices;
import monitoring.repository.dbrepointerface.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerService implements IMonitoringServices {
    IRepoEmployee repoEmployee;
    IRepoEmployer repoEmployer;
    IRepoTeam repoTeam;
    IRepoTask repoTask;
    IRepoPresence repoPresence;
    IRepoTeamMember repoTeamMember;

    private Map<String, IMonitoringEmployerObserver> loggedEmployers;
    private Map<String, IMonitoringEmployeeObserver> loggedEmployees;

    private final Integer defaultThreadsNo = 2;

    public ServerService(IRepoEmployee repoEmployee, IRepoEmployer repoEmployer, IRepoTeam repoTeam, IRepoTask repoTask, IRepoPresence repoPresence, IRepoTeamMember repoTeamMember) {
        this.repoEmployee = repoEmployee;
        this.repoEmployer = repoEmployer;
        this.repoTeam = repoTeam;
        this.repoTask = repoTask;
        this.repoPresence = repoPresence;
        this.repoTeamMember = repoTeamMember;
        loggedEmployers = new ConcurrentHashMap<>();
        loggedEmployees = new ConcurrentHashMap<>();

    }

    @Override
    public String greeting(Employer crt) {
        return "SALUT DIN SERVER: "  + crt;
    }

    @Override
    public synchronized Employer employer_logIn(Employer employer,IMonitoringEmployerObserver client) {
        System.out.println("SUNT IN LOG IN");
        Employer emp = repoEmployer.findOne(employer.getEmail());

        if(emp == null)
            throw new MonitoringException("Invalid email!");

        if(!emp.getPassword().equals(employer.getPassword()))
            throw new MonitoringException("Invalid password!");

       loggedEmployers.put(emp.getEmail(),client);


        return emp;
    }



    @Override
    public synchronized void employer_logOut(Employer employer, IMonitoringEmployerObserver client) {
        System.out.println("[SERVICE] Logged out employee" + employer);
        IMonitoringEmployerObserver localClient =  loggedEmployers.remove(employer.getEmail());
        if(localClient == null)
            throw new MonitoringException("Employee: " + employer.getEmail() + " is not logged in!");

        System.out.println("[SERVICE] Employee is logged out!!" + employer);
    }

    @Override
    public synchronized List<Employee> getAllEmployeesForTodayPresence() {

        List<Employee> employees = repoEmployee.findAll();

        for(Employee emp : employees){

            Presence p = repoPresence.findTodayPresenceFor(emp.getId());

            if(p != null) {
                emp.setDepartureTime(p.getDepartureTime());

                emp.setArrivalTime(p.getArrivalTime());
            }
        }

        return employees;
    }

    @Override
    public  synchronized List<Task> getAllTasks() {
        return repoTask.findAll();
    }

    @Override
    public synchronized List<Team> getAllTeams() {
        return repoTeam.findAll();
    }

    @Override
    public synchronized void createNewTeam(String teamName, List<EmployeeDTOTeam> empTeamsList) {
        repoTeam.save(new Team(teamName,empTeamsList.size()));

        Team teamCreated = repoTeam.findOne(teamName);

        for(EmployeeDTOTeam e : empTeamsList ){
            repoTeamMember.save(new TeamMember(teamCreated.getId(),e.getIdEmployee(),e.getMemberRole()));

        }

        notifyEmployees_newTeam();

    }

    @Override
    public synchronized void assignTask(Task selectedTask) {
        selectedTask.setStatus(TaskStatus.ASSIGNED);
        selectedTask.setAssignDate(LocalDateTime.now());
        repoTask.update(selectedTask);

        notifyEmployees_newTask();
    }

    @Override
    public List<Employee> getEmployeesForTop() {
        List<Employee> lst = repoEmployee.findAll();

        lst.sort((a,b)->{
            return  b.getNrTasksCompleted() - a.getNrTasksCompleted();
        });

        return lst;

    }

    @Override
    public Employee employee_logIn(Employee employee,Presence presence, IMonitoringEmployeeObserver client) {
        Employee emp = repoEmployee.findOne(employee.getEmail());

        if(emp == null)
            throw new MonitoringException("Invalid email!");

        if(!emp.getPassword().equals(employee.getPassword()))
            throw new MonitoringException("Invalid password!");

        emp.setEmployeeStatus(EmployeeStatus.AVAILABLE);
        repoEmployee.update(emp);
        presence.setIdEmployee(emp.getId());
        repoPresence.save(presence);

        loggedEmployees.put(emp.getEmail(),client);

        notifyEmployers_employeeStatusChange();


        return emp;
    }

    @Override
    public void employee_logOut(Employee employee,Presence presence, IMonitoringEmployeeObserver client) {

        System.out.println("[SERVICE] Logged out employee" + employee);
        IMonitoringEmployeeObserver localClient =  loggedEmployees.remove(employee.getEmail());
        if(localClient == null)
            throw new MonitoringException("Employee: " + employee.getEmail() + " is not logged in!");

        Presence p = repoPresence.findTodayPresenceFor(employee.getId());

        p.setDepartureTime(presence.getDepartureTime());
        repoEmployee.update(employee);
        repoPresence.update(p);

        notifyEmployers_employeeStatusChange();

        System.out.println("[SERVICE] Employee is logged out!!" + employee);
    }

    @Override
    public List<Task> getAllTasksForEmployee(Integer id) {
        List<Task> tasksEmp = new ArrayList<>();

        for(Task t : repoTask.findAll()){
            if(t.getIdEmployee() != null)
                if(t.getIdEmployee().equals(id))
                    tasksEmp.add(t);
        }
        return tasksEmp;
    }

    @Override
    public void resolveTask(Task t) {
        t.setStatus(TaskStatus.IN_PROGRESS);
        repoTask.update(t);
        notifyEmployers_taskStatusChange();
    }



    @Override
    public void finishTask(Task t,Employee employee) {
        t.setCompletionDate(LocalDateTime.now());
        t.setStatus(TaskStatus.FINISHED);

        repoTask.update(t);

        repoEmployee.update(employee);

        notifyEmployers_taskStatusChange();

    }

    @Override
    public List<TeamDTO> getAllTeamsForEmployee(Integer id) {

        List<TeamDTO> lst = new ArrayList<>();

        for(TeamMember tm :  repoTeamMember.findAll()){
            if(tm.getIdEmployee() == id){
                Team team = repoTeam.findOne(tm.getIdTeam());
                lst.add(new TeamDTO(team.getId(), team.getTeamName(), tm.getMemberRole()));
            }
        }

        return lst;
    }

    @Override
    public void changeEmployeeStatus(Employee employee) {
        repoEmployee.update(employee);
        notifyEmployers_employeeStatusChange();
    }

    private void notifyEmployers_employeeStatusChange() {

        List<Employee> employees = getAllEmployeesForTodayPresence();

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        for(IMonitoringEmployerObserver empObs : loggedEmployers.values()){
            if(empObs != null){
                executor.execute(()->{
                    try{
                        empObs.notifyEmployeeChangeStatus(employees);
                    }catch (Exception ex){
                        System.err.println(ex.getMessage());
                    }
                });
            }
        }

    }

    private void notifyEmployers_taskStatusChange() {
        List<Task> tasks = getAllTasks();

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        for(IMonitoringEmployerObserver empObs : loggedEmployers.values()){
            if(empObs != null){
                executor.execute(()->{
                    try{
                        empObs.notifyChangeTaskStatus(tasks);
                    }catch (Exception ex){
                        System.err.println(ex.getMessage());
                    }
                });
            }
        }

    }

    private void notifyEmployees_newTeam(){
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);


        for (Map.Entry<String, IMonitoringEmployeeObserver> entry : loggedEmployees.entrySet()) {
            IMonitoringEmployeeObserver obs = entry.getValue();

            Employee emp = repoEmployee.findOne(entry.getKey());

            List<TeamDTO> teams = getAllTeamsForEmployee(emp.getId());

            executor.execute(()->{
                try{
                        obs.notifyNewTeamIsCreated(teams);
                }catch (Exception ex){
                    System.err.println(ex.getMessage());
                }
            });

        }

    }

    private void notifyEmployees_newTask(){
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);


        for (Map.Entry<String, IMonitoringEmployeeObserver> entry : loggedEmployees.entrySet()) {
            IMonitoringEmployeeObserver obs = entry.getValue();

            Employee emp = repoEmployee.findOne(entry.getKey());

            List<Task> tasks = getAllTasksForEmployee(emp.getId());

            executor.execute(()->{
                try{
                    obs.notifyNewTaskIsAssigned(tasks);
                }catch (Exception ex){
                    System.err.println(ex.getMessage());
                }
            });

        }
    }


}
