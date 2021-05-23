import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monitoring.controller.EmployeeController;
import monitoring.controller.EmployerController;
import monitoring.controller.LogInController;
import monitoring.service.IMonitoringServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.io.IOException;

public class StartSpringClientFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IMonitoringServices server = (IMonitoringServices) factory.getBean("monitoringService");
            System.out.println("Obtained a reference to remote char server");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/logIn.fxml"));
            Parent root=loader.load();

            FXMLLoader loader_employer = new FXMLLoader();
            loader_employer.setLocation(getClass().getResource("view/employerGUI.fxml"));
            Parent employerParent = loader_employer.load();

            EmployerController employerController = loader_employer.getController();
            employerController.setService(server);


            FXMLLoader loader_employee = new FXMLLoader();
            loader_employee.setLocation(getClass().getResource("view/employeeGUI.fxml"));
            Parent employeeParent = loader_employee.load();

            EmployeeController employeeController = loader_employee.getController();
            employeeController.setService(server);


            LogInController logInController = loader.getController();

            logInController.setEmployerController(employerController);
            logInController.setEmployerStage(employerParent);

            logInController.setEmployeeController(employeeController);
            logInController.setEmployeeStage(employeeParent);

            primaryStage.setScene(new Scene(root));

            logInController.setMyStage(primaryStage);

            primaryStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
