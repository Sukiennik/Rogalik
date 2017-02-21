package pl.rogalik.client.controller.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class CreateDeleteController {

    @FXML
    private TextField nameField;
    @FXML
    private Button done;
    @FXML
    private Button cancel;
    @FXML
    private ImageView imageView;
    @FXML
    private Label headerLabel;

    private Stage dialogStage;

    @FXML
    private void initialize() {
        Platform.runLater(() -> nameField.requestFocus());
        done.defaultButtonProperty().bind(done.focusedProperty());
        cancel.defaultButtonProperty().bind(cancel.focusedProperty());

        imageView.setFitHeight(128);
        imageView.setFitWidth(128);

        nameField.setOnKeyPressed(event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                done.requestFocus();
            }
        });
        done.setOnKeyPressed(event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                dialogStage.hide();
            }
        });
        cancel.setOnKeyPressed(event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                nameField = null;
                dialogStage.hide();
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    String getNameField() {
        return nameField.getText();
    }

    public void setCreateHero(){
        headerLabel.setText("Podaj nazwę postaci");
        done.setText("Utwórz");
        cancel.setText("Anuluj");
        dialogStage.getScene().getStylesheets().add("/css/heroCreate.css");
        Image image = new Image("/images/characterIcon.png" );
        imageView.setImage(image);
    }

    public void setDeleteUser(){
        headerLabel.setText("Podaj nazwę użytkownika");
        done.setText("Usuń");
        cancel.setText("Anuluj");
        dialogStage.getScene().getStylesheets().add("/css/userDelete.css");
        Image image = new Image("/images/user.png" );
        imageView.setImage(image);
    }
}
