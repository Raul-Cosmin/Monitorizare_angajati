package monitoring.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task extends Entity<Integer>{

    private String description;
    private LocalDateTime assignDate;
    private LocalDateTime deadline;
    private LocalDateTime completionDate;
    private TaskStatus status;
    private Integer idEmployee;
    private Integer idTeam;


    public Task(){}

    public Task(Integer id){
        super(id);

    }

    public Task(String description){
        super(0);
        this.description = description;
        this.status = TaskStatus.IN_PROGRESS;

    }

    public Task(Integer integer, String description, LocalDateTime assignDate, LocalDateTime deadline, LocalDateTime completionDate, TaskStatus status) {
        super(integer);
        this.description = description;
        this.assignDate = assignDate;
        this.deadline = deadline;
        this.completionDate = completionDate;
        this.status = status;


    }
    public Task( String description, LocalDateTime assignDate, LocalDateTime deadline, LocalDateTime completionDate, TaskStatus status) {
        this(0,description,assignDate,deadline,completionDate,status);
    }



    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", assignDate=" + assignDate +
                ", deadline=" + deadline +
                ", completionDate=" + completionDate +
                ", status=" + status +
                ", idEmployee=" + idEmployee +
                ", idTeam=" + idTeam +
                '}';
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDateTime assignDate) {
        this.assignDate = assignDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }



}
