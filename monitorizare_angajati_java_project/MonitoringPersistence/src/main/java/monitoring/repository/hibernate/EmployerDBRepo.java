package monitoring.repository.hibernate;


import monitoring.domain.Employer;
import monitoring.domain.exception.MonitoringException;
import monitoring.domain.validation.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import monitoring.repository.dbrepointerface.IRepoEmployer;

import java.util.ArrayList;
import java.util.List;

public class EmployerDBRepo implements IRepoEmployer {
    private SessionFactory sessionFactory;
    private Validator<Employer> validator;

    public EmployerDBRepo(Validator<Employer> validator, SessionFactory sessionFactory){
        this.validator = validator;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Employer findOne(String email) {
        if(email.equals(""))
            throw new MonitoringException("Email must be not empty!");

        Employer employer = null;

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{

                tx = session.beginTransaction();

                String hql = "FROM Employer E where E.email = :employer_email";
                employer = (Employer) session.createQuery(hql).setParameter("employer_email",email).setMaxResults(1).uniqueResult();

                tx.commit();

            }catch (RuntimeException ex){
                System.err.println("EmployeeRepoHibernate ex="+ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        return  employer;
    }


    @Override
    public void save(Employer entity) {
        if(entity == null)
            throw new MonitoringException("Entity must be not null");

        if(findOne(entity.getEmail()) != null)
            throw new MonitoringException("Entity already exist in DB!");

        validator.validate(entity);

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
    public List<Employer> findAll() {
        List<Employer> employers = new ArrayList<>();


        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                String hql = "FROM Employer E order by E.id asc";
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

}
