package monitoring.domain;

import java.time.LocalDateTime;

public class Presence extends Entity<Integer>{
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private Integer idEmployee;

    public Presence(){}

    public Presence(Integer integer, LocalDateTime arrivalTime, LocalDateTime departureTime,Integer idEmployee) {
        super(integer);
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.idEmployee = idEmployee;
    }
    public Presence(LocalDateTime arrivalTime, LocalDateTime departureTime,Integer idEmployee) {
        super(0);
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.idEmployee = idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

}
