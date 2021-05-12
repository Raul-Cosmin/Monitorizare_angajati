package monitorizare.domain;

import monitorizare.repository.hibernate.EmployeeDBRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class Employee extends Entity<Integer>{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer nrTasksCompleted;
    private EmployeeStatus employeeStatus;
    private Stack<Presence> presences = new Stack<>();
    private List<Task> tasks = new ArrayList<>();

    public Employee(){

    }

    public Employee(Integer id, String firstName, String lastName, String email, String password) {

        super(id);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;



    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Employee(String firstName, String lastName, String email, String password) {
        super(0);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;


    }


    public void confirmPresence(LocalDateTime arrivalTime, LocalDateTime departureTime){
        presences.push(new Presence(arrivalTime,departureTime,this.id));

    }

    public Presence getCurrentPresence(){
        return presences.peek();
    }

    public Stack<Presence> getPresences() {
        return presences;
    }

    public void setPresences(Stack<Presence> presences) {
        this.presences = presences;
    }

    public Integer getNrTasksCompleted() {
        return nrTasksCompleted;
    }

    public void setNrTasksCompleted(Integer nrTasksCompleted) {
        this.nrTasksCompleted = nrTasksCompleted;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nrTaskCompleted='" + nrTasksCompleted + '\'' +
                '}';
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
