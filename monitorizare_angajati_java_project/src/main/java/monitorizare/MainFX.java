package monitorizare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monitorizare.controller.LogInController;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/logIn.fxml"));
            AnchorPane root = loader.load();
            LogInController logInController = loader.getController();
            logInController.setLogInStage(stage);





            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();




        } catch (Exception ex) {
            System.err.println(ex.getMessage());

        }
    }
}
