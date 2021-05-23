package monitoring.repository.dbrepointerface;


import monitoring.domain.Employer;
import monitoring.repository.ICrudRepository;

public interface IRepoEmployer extends ICrudRepository<Integer, Employer> {
    Employer findOne(String email);

}
