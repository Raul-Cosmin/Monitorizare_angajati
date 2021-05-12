package monitorizare.repository.dbrepointerface;

import monitorizare.domain.Employee;
import monitorizare.domain.Employer;
import monitorizare.repository.ICrudRepository;

public interface IRepoEmployer extends ICrudRepository<Integer, Employer> {
    Employer findOne(String email);

}
