package app.util;

import app.MainApp;
import app.controllers.PopupController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public final class DialogUtils {

    /**
     * Creates popup window with custom message. It is visible for a given time interval.
     * @param message Message that you want to show
     * @param duration lifetime of window (in seconds)
     */
    public static void popupWindow(String message,int duration){

        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/popup.fxml"));
            Parent root = loader.load();
            PopupController popupController = loader.getController();
            popupController.setPopupText(message);

            Scene scene  = new Scene(root);
            Stage popup = new Stage();
            popup.setScene(scene);

            PauseTransition delay = new PauseTransition(Duration.seconds(duration));
            delay.setOnFinished(e->popup.hide());
            popup.show();
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Exit");
        confirmationDialog.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return  result;
    }
}
