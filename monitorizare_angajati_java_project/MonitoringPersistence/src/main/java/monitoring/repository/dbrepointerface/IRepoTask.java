package monitoring.repository.dbrepointerface;

import monitoring.domain.Task;
import monitoring.repository.ICrudRepository;

public interface IRepoTask extends ICrudRepository<Integer, Task> {
    void update(Task task);
}
