package pl.rogalik.client.utils;

import pl.rogalik.client.Main;
import pl.rogalik.client.MainContext;
import pl.rogalik.client.controller.game.GameController;
import pl.rogalik.client.controller.login.LoginController;
import pl.rogalik.client.controller.menu.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created 11.12.16.
 */
public final class SceneSelector {

    private static final String LOGIN_FXML = "/pl/rogalik/client/view/login/loginFXML.fxml";
    private static final String GAME_FXML = "/pl/rogalik/client/view/game/gameFXML.fxml";
    private static final String MENU_FXML = "/pl/rogalik/client/view/menu/menuFXML.fxml";

    private static MainContext context = MainContext.getContext();

    public static void goToLoginScreen() {
        try {
            LoginController login = (LoginController) SceneSelector.replaceSceneContent(context.getStage(), SceneSelector.LOGIN_FXML);
            login.setAppContext(context);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void goToGameScreen() {
        try {
            GameController game = (GameController) SceneSelector.replaceSceneContent(context.getStage(), SceneSelector.GAME_FXML);
            game.setAppContext(context);
            game.prepareFirstView();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void goToMenuScreen(){
        try {
            MenuController menu = (MenuController) SceneSelector.replaceSceneContent(context.getStage(), SceneSelector.MENU_FXML);
            menu.setAppContext(context);
            menu.prepareFirstView();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Initializable replaceSceneContent(Stage stage, String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        Parent page;
        try (InputStream in = Main.class.getResourceAsStream(fxml)) {
            page = loader.load(in);
        }
        Scene scene = new Scene(page, 1024, 748);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
