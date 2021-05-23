package monitoring.repository.dbrepointerface;


import monitoring.domain.Presence;
import monitoring.repository.ICrudRepository;

public interface IRepoPresence extends ICrudRepository<Integer, Presence> {
    Presence findTodayPresenceFor(Integer idEmployee);

    void update(Presence presence);
}
