package monitorizare;

import monitorizare.domain.*;
import monitorizare.repository.dbrepointerface.*;
import monitorizare.repository.hibernate.*;
import monitorizare.service.Service;
import monitorizare.validation.EmployeeValidator;
import monitorizare.validation.EmployerValidator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestApp {
    private static SessionFactory sessionFactory;

    private static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    public static void initialize(){

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() //configures setting from hibernate,cfg.xml
                .build();
        try{
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        }catch (Exception ex){
            System.err.println("Exception " + ex);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void main(String[] args) {
        try{


            initialize();

            IRepoEmployee repoEmployee = new EmployeeDBRepo(new EmployeeValidator(),sessionFactory);

            IRepoEmployer repoEmployer = new EmployerDBRepo(new EmployerValidator(),sessionFactory);

            IRepoPresence repoPresence = new PresenceDBRepo(sessionFactory);

            IRepoTask repoTask = new TaskDBRepo(sessionFactory);

            IRepoTeam repoTeam = new TeamDBRepo(sessionFactory);

            IRepoTeamMember repoTeamMember = new TeamMemberDBRepo(sessionFactory);


            Service service = new Service(repoEmployee,repoEmployer,repoTeam,repoTask,repoPresence,repoTeamMember);

            List<EmployeeDTO> emps = service.getAllEmployeesForTodayPresence();

            for(EmployeeDTO e : emps){
                System.out.println(e);
            }

//            Team team = new Team("Workers");
//            repoTeam.save(team);


//            Task task = new Task("Organizer app");
//            repoTask.save(task);
//            task.setId(1);
//            task.setIdEmployee(3);
//            task.setDeadline(LocalDateTime.now());
//
//            repoTask.update(task);


//            Task task = new Task("De construit");
//
//            repoTask.save(task);
//
//            for(Task t : repoTask.findAll()){
//                System.out.println(t);
//            }

//            Presence presence = new Presence(LocalDateTime.now(),LocalDateTime.now(),1);
//
//            repoPresence.save(presence);
////
//            Employer employer = new Employer("Costi","Vlad","valud@yahoo.com","0000");
//
//            repoEmployer.save(employer);
//


//            Employee employee = new Employee("Ionut","Ioan","ionel@yahoo.com","0000");
//            employee.setEmployeeStatus(EmployeeStatus.AVAILABLE);
//
//            repoEmployee.save(employee);
//
//            for(Employee emp: repoEmployee.findAll()){
//                System.out.println(emp);
//            }

        }catch (Exception ex){
            System.err.println("Exception "+ex);
        }
        finally {
            close();
        }
    }
}
