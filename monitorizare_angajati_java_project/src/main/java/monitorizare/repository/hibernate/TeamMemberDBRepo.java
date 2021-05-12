package monitorizare.repository.hibernate;

import monitorizare.domain.Team;
import monitorizare.domain.TeamMember;
import monitorizare.exception.MonitoringException;
import monitorizare.repository.dbrepointerface.IRepoTeamMember;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TeamMemberDBRepo implements IRepoTeamMember {

    private SessionFactory sessionFactory;

    public TeamMemberDBRepo(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(TeamMember entity) {
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
    public List<TeamMember> findAll() {

        List<TeamMember> teamMembers = new ArrayList<>();


        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                String hql = "FROM Team T order by T.id asc";
                teamMembers =  session.createQuery(hql).list();

                tx.commit();

            }catch (RuntimeException ex){
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }

        return teamMembers;
    }
}
