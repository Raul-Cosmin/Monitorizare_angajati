package monitorizare.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

public class MessageAlert {
    static boolean showMessageConfirmation( String header){
        Alert message=new Alert(Alert.AlertType.CONFIRMATION);
        message.setHeaderText(header);

        message.initOwner(null);
        message.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result =  message.showAndWait();

        if(result.get().getText().equals("OK")) {
            return true;
        }
        return false;
    }

    public static void showMessage(Alert.AlertType type,String text){
        Alert message = new Alert(type);
        message.setTitle("Message");
        message.setContentText(text);
        message.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        message.showAndWait();


    }

    public static void showErrorMessage( String text){


        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setHeaderText(null);
        message.setTitle("Error message");
        message.setContentText(text);
        message.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        message.showAndWait();
    }


}
