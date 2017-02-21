package pl.rogalik.client.utils;

import pl.rogalik.client.Main;
import pl.rogalik.client.controller.game.EquipItemController;
import pl.rogalik.client.controller.game.GameOverController;
import pl.rogalik.client.controller.menu.CreateDeleteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogManager {

    private static final String CREATE_DELETE_FXML = "/pl/rogalik/client/view/menu/deleteCreateFXML.fxml";
    private static final String EQUIP_ITEM_FXML = "/pl/rogalik/client/view/game/equipItemFXML.fxml";
    private static final String GAME_OVER_FXML = "/pl/rogalik/client/view/game/gameOverFXML.fxml";
    private FXMLLoader loader;
    private Stage dialogStage;

    public DialogManager(){
        this.dialogStage = new Stage();
        dialogStage.getIcons().add(new Image("/images/rogalik.png"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
    }

    private void loadDialog(String fxml, String title){
        try{
            this.loader = new FXMLLoader();
            dialogStage.setTitle(title);
            loader.setLocation(Main.class.getResource(fxml));
            BorderPane page = loader.load();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runHeroCreationDialog(){
        loadDialog(CREATE_DELETE_FXML, "Utwórz postać");
        CreateDeleteController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCreateHero();
        dialogStage.showAndWait();
    }

    public void runUserDeletionDialog(){
        loadDialog(CREATE_DELETE_FXML, "Usuń gracza");
        CreateDeleteController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setDeleteUser();
        dialogStage.showAndWait();
    }

    public void runEquipItemDialog() {
        loadDialog(EQUIP_ITEM_FXML, "Użyj przedmiot");
        EquipItemController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    public void runGameOverDialog(){
        loadDialog(GAME_OVER_FXML, "Przegrałeś");
        GameOverController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    public Object getController(){
        return loader.getController();
    }
}
