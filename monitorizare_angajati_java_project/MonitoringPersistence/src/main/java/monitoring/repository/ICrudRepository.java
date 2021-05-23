package monitoring.repository;

import monitoring.domain.Entity;

import java.util.List;

public interface ICrudRepository<ID,E extends Entity<ID>> {
    /**
     *
     * @param entity
     */
    void save(E entity);


    /**
     *
     * @return all entities from DB
     */
    List<E> findAll();

    /**
     * Close connection with DB
     */

}
