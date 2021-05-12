package monitorizare.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String arrivalTime = "-";
    private String departureTime = "-";
    private EmployeeStatus status;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public EmployeeDTO(int id, String firstName, String lastName,  EmployeeStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getArrivalTime_time() {
        return LocalDateTime.parse(arrivalTime,formatter);
    }
    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {

        this.arrivalTime = arrivalTime.format(formatter);
    }

    public LocalDateTime getDepartureTime_time() {
        return LocalDateTime.parse(departureTime,formatter);
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime.format(formatter);
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }
}
