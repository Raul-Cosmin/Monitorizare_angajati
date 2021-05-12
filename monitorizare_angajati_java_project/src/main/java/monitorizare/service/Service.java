package monitorizare.service;

import monitorizare.domain.*;
import monitorizare.exception.MonitoringException;
import monitorizare.observer.Observable;
import monitorizare.observer.Observer;
import monitorizare.repository.dbrepointerface.*;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable {
    IRepoEmployee repoEmployee;
    IRepoEmployer repoEmployer;
    IRepoTeam repoTeam;
    IRepoTask repoTask;
    IRepoPresence repoPresence;
    IRepoTeamMember repoTeamMember;

    List<Observer> observers = new ArrayList<>();


    public Service(IRepoEmployee repoEmployee, IRepoEmployer repoEmployer, IRepoTeam repoTeam, IRepoTask repoTask, IRepoPresence repoPresence, IRepoTeamMember repoTeamMember) {
        this.repoEmployee = repoEmployee;
        this.repoEmployer = repoEmployer;
        this.repoTeam = repoTeam;
        this.repoTask = repoTask;
        this.repoPresence = repoPresence;
        this.repoTeamMember = repoTeamMember;
    }

    public List<EmployeeDTO> getAllEmployeesForTodayPresence(){
        List<EmployeeDTO> employees = new ArrayList<>();

        for(Employee emp : repoEmployee.findAll()){

            Presence p = repoPresence.findTodayPresenceFor(emp.getId());
            EmployeeDTO empDto = new EmployeeDTO(emp.getId(),emp.getFirstName(), emp.getLastName(), emp.getEmployeeStatus());

            if(p != null)
            {
                if(p.getArrivalTime() != null)
                    empDto.setArrivalTime(p.getArrivalTime());
                if(p.getDepartureTime() != null)
                    empDto.setDepartureTime(p.getDepartureTime());
            }

            employees.add(empDto);

        }

        return employees;
    }


    public Employer logInEmployer(String emailEmployer, String passwordEmployer) {

        Employer emp = repoEmployer.findOne(emailEmployer);

        if(emp == null)
            throw new MonitoringException("Invalid email!");

        if(!emp.getPassword().equals(passwordEmployer))
            throw new MonitoringException("Invalid password!");

        return emp;

    }

    public void createNewTeam(String teamName, List<EmployeeDTOTeam> empTeamsList) {

        repoTeam.save(new Team(teamName,empTeamsList.size()));

        Team teamCreated = repoTeam.findOne(teamName);

        for(EmployeeDTOTeam e : empTeamsList ){
            repoTeamMember.save(new TeamMember(teamCreated.getId(),e.getIdEmployee(),e.getMemberRole()));

        }
    }

    public List<Task> getAllTasks() {
        return repoTask.findAll();
    }

    public List<Team> getAllTeams() {
        return repoTeam.findAll();
    }

    public void assignTask(Task selectedTask) {
        selectedTask.setStatus(TaskStatus.IN_PROGRESS);
        repoTask.update(selectedTask);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers)
            o.update();
    }
}
