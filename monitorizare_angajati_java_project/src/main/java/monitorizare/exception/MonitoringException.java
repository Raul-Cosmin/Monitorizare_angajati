package monitorizare.exception;

public class MonitoringException extends RuntimeException{
    public MonitoringException() {
        super();
    }

    public MonitoringException(String message) {
        super(message);
    }
}