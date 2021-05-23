package monitoring.service;

import monitoring.domain.*;

import java.util.List;

public interface IMonitoringServices {

    String greeting(Employer crt);

    Employer employer_logIn(Employer employer, IMonitoringEmployerObserver client);

    void employer_logOut(Employer employer, IMonitoringEmployerObserver client);


    List<monitoring.domain.Employee> getAllEmployeesForTodayPresence();

    List<monitoring.domain.Task> getAllTasks();

    List<monitoring.domain.Team> getAllTeams();

    void createNewTeam(String teamName, List<EmployeeDTOTeam> empTeamsList);

    void assignTask(Task selectedTask);

    List<Employee> getEmployeesForTop();

    Employee employee_logIn(Employee employeeCrt,Presence presence, IMonitoringEmployeeObserver client);

    void employee_logOut(Employee employee,Presence presence, IMonitoringEmployeeObserver client);

    List<Task> getAllTasksForEmployee(Integer id);

    void resolveTask(Task t);

    void finishTask(Task t,Employee employee);

    List<TeamDTO> getAllTeamsForEmployee(Integer id);

    void changeEmployeeStatus(Employee employee);
}
