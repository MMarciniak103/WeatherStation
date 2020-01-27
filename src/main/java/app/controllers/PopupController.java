package app.controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class PopupController {

    @FXML
    public AnchorPane popupPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField popupText;

    @FXML
    void initialize() {
        assert popupText != null : "fx:id=\"popupText\" was not injected: check your FXML file 'popup.fxml'.";

        Platform.runLater(()->popupPane.requestFocus());

    }


    public void setPopupText(String message){
        this.popupText.textProperty().setValue(message);
    }
}