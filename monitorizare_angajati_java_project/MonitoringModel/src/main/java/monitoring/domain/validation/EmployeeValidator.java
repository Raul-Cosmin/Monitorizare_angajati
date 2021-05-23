package monitoring.domain.validation;


import monitoring.domain.Employee;
import monitoring.domain.exception.MonitoringException;

public class EmployeeValidator implements Validator<Employee> {
    @Override
    public void validate(Employee entity) throws RuntimeException {
        String err = "";

        if(entity.getFirstName().isEmpty())
            err += "Invalid first name!\n";

        if(entity.getLastName().isEmpty())
            err += "Invalid last name!\n";

        if(entity.getEmail().isEmpty())
            err += "Invalid email!\n";

        if(entity.getPassword().isEmpty())
            err += "Invalid password!\n";

        if(!err.isEmpty())
            throw new MonitoringException(err);

    }
}
