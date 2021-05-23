package monitoring.domain;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskDTO {
    private Integer id;
    private String description;
    private TaskStatus status;
    private Integer idEmployee;
    private Integer idTeam;
    private String assignDate_str = "-";
    private String deadline_str = "-";
    private String completionDate_str = "-";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TaskDTO(Integer id, String description, TaskStatus status, Integer idEmployee, Integer idTeam) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.idEmployee = idEmployee;
        this.idTeam = idTeam;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public LocalDateTime getDateDeadline(){
        if(!deadline_str.equals("-"))
            return LocalDateTime.parse(deadline_str,formatter);
        return null;
    }
    public LocalDateTime getDateAssign(){
        if(!assignDate_str.equals("-"))
             return LocalDateTime.parse(assignDate_str,formatter);
        return null;
    }
    public LocalDateTime getDateCompletion(){
        if(!completionDate_str.equals("-"))
            return LocalDateTime.parse(completionDate_str,formatter);
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
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

    public String getAssignDate_str() {
        return assignDate_str;
    }

    public void setAssignDate_str(LocalDateTime assignDate) {
        if(assignDate != null)
            this.assignDate_str = assignDate.format(formatter);
    }

    public String getDeadline_str() {
        return deadline_str;

    }

    public void setDeadline_str(LocalDateTime deadline) {
        if(deadline != null)
            this.assignDate_str = deadline.format(formatter);
    }

    public String getCompletionDate_str() {
        return completionDate_str;
    }

    public void setCompletionDate_str(LocalDateTime completionDate) {
        if(completionDate != null)
            this.completionDate_str = completionDate.format(formatter);
    }
}
