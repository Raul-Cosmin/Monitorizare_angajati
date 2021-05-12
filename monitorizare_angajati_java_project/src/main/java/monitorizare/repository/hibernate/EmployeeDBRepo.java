package monitorizare.repository.hibernate;

import monitorizare.domain.Employee;
import monitorizare.domain.Entity;
import monitorizare.exception.MonitoringException;
import monitorizare.repository.dbrepointerface.IRepoEmployee;
import monitorizare.validation.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDBRepo implements IRepoEmployee {
    private SessionFactory sessionFactory;
    private Validator<Employee> validator;

    public EmployeeDBRepo(Validator<Employee> validator, SessionFactory sessionFactory){
        this.validator = validator;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Employee findOne(String email) {
        if(email.equals(""))
            throw new MonitoringException("Email must be not empty!");

        Employee employee = null;

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{

                tx = session.beginTransaction();

                String hql = "FROM Employee E where E.email = :employee_email";
                employee = (Employee) session.createQuery(hql).setParameter("employee_email",email).setMaxResults(1).uniqueResult();

                tx.commit();

            }catch (RuntimeException ex){
                System.err.println("EmployeeRepoHibernate ex="+ex);
                if(tx != null)
                    tx.rollback();
            }
        }
        return  employee;
    }


    @Override
    public void save(Employee entity) {
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
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();


        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();

                String hsql = "FROM Employee E order by E.id asc";
                employees =  session.createQuery(hsql).list();

                tx.commit();

            }catch (RuntimeException ex){
                if (tx != null)
                    tx.rollback();

                throw new MonitoringException("Exception from Hibernate=" + ex);
            }
        }

        return  employees;
    }


}
