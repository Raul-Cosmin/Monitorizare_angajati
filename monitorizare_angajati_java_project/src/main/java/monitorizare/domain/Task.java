package monitorizare.domain;

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

    private String assignDate_str;
    private String deadline_str;
    private String completionDate_str;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public Task(){}

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

    public String getAssignDate_str() {
        if(this.assignDate != null)
            this.assignDate_str = assignDate.format(formatter);
        return assignDate_str;
    }

    public void setAssignDate_str(String assignDate_str) {
        this.assignDate_str = assignDate_str;
        this.assignDate = LocalDateTime.parse(assignDate_str,formatter);
    }

    public String getDeadline_str() {
        if(deadline != null)
            this.deadline_str = deadline.format(formatter);
        return deadline_str;
    }

    public void setDeadline_str(String deadline_str) {
        this.deadline_str = deadline_str;
        this.deadline = LocalDateTime.parse(deadline_str,formatter);
    }

    public String getCompletionDate_str() {
        if(completionDate != null)
            this.completionDate_str = completionDate.format(formatter);
        return completionDate_str;
    }

    public void setCompletionDate_str(String completionDate_str) {
        this.completionDate_str = completionDate_str;
        this.completionDate = LocalDateTime.parse(completionDate_str,formatter);
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
