package monitoring.repository.hibernate;


import monitoring.domain.Employee;
import monitoring.domain.Presence;
import monitoring.domain.exception.MonitoringException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import monitoring.repository.dbrepointerface.IRepoPresence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PresenceDBRepo implements IRepoPresence {
    private SessionFactory sessionFactory;

    public PresenceDBRepo(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Presence entity) {
        if(entity == null)
            throw new MonitoringException("Entity must be not null!");

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;

            try{
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate= " + ex);
            }
        }
    }

    @Override
    public List<Presence> findAll() {
        List<Presence> presences = new ArrayList<>();

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                String hql = "FROM Presence P order by P.id asc";
                presences = session.createQuery(hql).list();

                tx.commit();
            }catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }
        return presences;
    }

    @Override
    public Presence findTodayPresenceFor(Integer idEmployee) {
        Presence presence = null;

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                LocalDate localDate = LocalDate.now();
                LocalDateTime localDateTimeToday = LocalDateTime.of(localDate.getYear(),localDate.getMonth(),localDate.getDayOfMonth(),00,00);


                String hql = "FROM Presence P WHERE P.idEmployee = :idEmployee";
                List<Presence> presences = (List<Presence>) session.createQuery(hql).setParameter("idEmployee",idEmployee).list();

                for(Presence p : presences)
                    if(p.getArrivalTime().isAfter(localDateTimeToday)) {
                        presence = p;
                        break;
                    }

                tx.commit();
            }catch (RuntimeException ex){
                if(tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }
        return presence;
    }

    @Override
    public void update(Presence presence) {
        if(presence == null)
            throw new MonitoringException("Invalid presence for update!");

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                Presence presence1 = session.load(Presence.class, presence.getId());
                presence1.setDepartureTime(presence.getDepartureTime());

                tx.commit();

            }catch (RuntimeException ex){
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }
    }
}
