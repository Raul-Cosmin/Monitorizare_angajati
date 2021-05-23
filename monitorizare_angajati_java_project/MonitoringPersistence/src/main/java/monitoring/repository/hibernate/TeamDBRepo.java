package monitoring.repository.hibernate;


import monitoring.domain.Team;
import monitoring.domain.exception.MonitoringException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import monitoring.repository.dbrepointerface.IRepoTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamDBRepo implements IRepoTeam {
    private SessionFactory sessionFactory;

    public TeamDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Team entity) {
        if(entity == null)
            throw new MonitoringException("Entity must be not null!");


        if(findOne(entity.getTeamName()) != null)
            throw new MonitoringException("A team already exist with this name!");



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
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();


        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                String hql = "FROM Team T order by T.id asc";
                teams =  session.createQuery(hql).list();

                tx.commit();

            }catch (RuntimeException ex){
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }

        return teams;
    }

    @Override
    public Team findOne(String teamName) {
        if(teamName.equals(""))
            throw new MonitoringException("Team name must be not empty!");

        Team team= null;

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{

                tx = session.beginTransaction();

                String hql = "FROM Team T where T.teamName = :team_name";
                team = (Team) session.createQuery(hql).setParameter("team_name",teamName).setMaxResults(1).uniqueResult();

                tx.commit();

            }catch (RuntimeException ex){
                System.err.println("EmployeeRepoHibernate ex="+ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        return team;
    }

    @Override
    public Team findOne(Integer id) {
        if(id < 0)
            throw new MonitoringException("Invalid id for team!");

        Team team= null;

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{

                tx = session.beginTransaction();

                String hql = "FROM Team T where T.id = :id";
                team = (Team) session.createQuery(hql).setParameter("id",id).setMaxResults(1).uniqueResult();

                tx.commit();

            }catch (RuntimeException ex){
                System.err.println("EmployeeRepoHibernate ex="+ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        return team;
    }
}
