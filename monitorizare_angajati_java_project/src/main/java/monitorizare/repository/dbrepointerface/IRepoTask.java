package monitorizare.repository.dbrepointerface;

import monitorizare.domain.Task;
import monitorizare.repository.ICrudRepository;

public interface IRepoTask extends ICrudRepository<Integer, Task> {
    void update(Task task);
}
