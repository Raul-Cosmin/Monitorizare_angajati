package monitorizare.repository.dbrepointerface;

import monitorizare.domain.Employee;
import monitorizare.repository.ICrudRepository;

public interface IRepoEmployee extends ICrudRepository<Integer,Employee> {
    Employee findOne(String email);

}
