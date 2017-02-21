package pl.rogalik.client.controller.game;

import pl.rogalik.client.MainContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private Label message;
    @FXML
    private Button menu;
    @FXML
    private Button login;

    private Stage dialogStage;
    private boolean isStillPlaying;

    @FXML
    private void initialize() {

        menu.defaultButtonProperty().bind(menu.focusedProperty());
        login.defaultButtonProperty().bind(login.focusedProperty());

        this.message.setText(MainContext.getContext().getChosenHero().getName() + " został pokonany!");

        login.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                isStillPlaying = false;
                this.dialogStage.hide();
            }
        });
        menu.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                isStillPlaying = true;
                this.dialogStage.hide();
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    boolean getIsStillPlaying() {return isStillPlaying; }

}

