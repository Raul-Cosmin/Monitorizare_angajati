package monitoring.repository.dbrepointerface;


import monitoring.domain.Team;
import monitoring.repository.ICrudRepository;

public interface IRepoTeam extends ICrudRepository<Integer, Team> {
    Team findOne(String teamName);

    Team findOne(Integer id);

}
