package monitorizare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monitorizare.controller.LogInController;
import monitorizare.domain.EmployeeDTO;
import monitorizare.repository.dbrepointerface.*;
import monitorizare.repository.hibernate.*;
import monitorizare.service.Service;
import monitorizare.validation.EmployeeValidator;
import monitorizare.validation.EmployerValidator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class MainFX extends Application {

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            initialize();

            IRepoEmployee repoEmployee = new EmployeeDBRepo(new EmployeeValidator(),sessionFactory);

            IRepoEmployer repoEmployer = new EmployerDBRepo(new EmployerValidator(),sessionFactory);

            IRepoPresence repoPresence = new PresenceDBRepo(sessionFactory);

            IRepoTask repoTask = new TaskDBRepo(sessionFactory);

            IRepoTeam repoTeam = new TeamDBRepo(sessionFactory);

            IRepoTeamMember repoTeamMember = new TeamMemberDBRepo(sessionFactory);

            Service service = new Service(repoEmployee,repoEmployer,repoTeam,repoTask,repoPresence,repoTeamMember);



            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/logIn.fxml"));
            AnchorPane root = loader.load();
            LogInController logInController = loader.getController();
            logInController.setLogInStage(stage);
            logInController.setService(service);

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();




        } catch (Exception ex) {
            System.err.println(ex.getMessage());

        }finally {
//            close();
        }
    }
}
