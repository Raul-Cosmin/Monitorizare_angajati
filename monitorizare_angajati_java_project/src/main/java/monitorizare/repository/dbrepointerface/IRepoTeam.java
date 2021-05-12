package monitorizare.repository.dbrepointerface;

import monitorizare.domain.Team;
import monitorizare.repository.ICrudRepository;

public interface IRepoTeam extends ICrudRepository<Integer, Team> {
    Team findOne(String teamName);

}
