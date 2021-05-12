package monitorizare.repository.dbrepointerface;

import monitorizare.domain.Presence;
import monitorizare.repository.ICrudRepository;

public interface IRepoPresence extends ICrudRepository<Integer, Presence> {
    Presence findTodayPresenceFor(Integer idEmployee);
}
