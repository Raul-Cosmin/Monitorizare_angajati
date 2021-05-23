package monitoring.repository.hibernate;


import monitoring.domain.Task;
import monitoring.domain.exception.MonitoringException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import monitoring.repository.dbrepointerface.IRepoTask;

import java.util.ArrayList;
import java.util.List;

public class TaskDBRepo implements IRepoTask {
    private SessionFactory sessionFactory;

    public TaskDBRepo(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Task entity) {
        if(entity == null)
            throw new MonitoringException("Entity must be not null");


        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> employers = new ArrayList<>();


        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                String hql = "FROM Task T order by T.id asc";
                employers =  session.createQuery(hql).list();

                tx.commit();

            }catch (RuntimeException ex){
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }

        return employers;
    }

    @Override
    public void update(Task task) {
        if(task == null)
            throw new MonitoringException("Invalid task for update!");

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                Task task1 = session.load(Task.class, task.getId());
                task1.setDescription(task.getDescription());
                task1.setAssignDate(task.getAssignDate());
                task1.setDeadline(task.getDeadline());
                task1.setCompletionDate(task.getCompletionDate());
                task1.setStatus(task.getStatus());
                task1.setIdEmployee(task.getIdEmployee());
                task1.setIdTeam(task.getIdTeam());

                tx.commit();

            }catch (RuntimeException ex){
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }
    }
}
