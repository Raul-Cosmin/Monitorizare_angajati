package monitoring.repository.dbrepointerface;


import monitoring.domain.Employee;
import monitoring.repository.ICrudRepository;

public interface IRepoEmployee extends ICrudRepository<Integer, Employee> {
    Employee findOne(String email);

    void update(Employee emp);

}
