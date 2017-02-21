package pl.rogalik.client;

import pl.rogalik.client.utils.SceneSelector;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        double MIN_WINDOW_WIDTH = 1024;
        double MIN_WINDOW_HEIGHT = 768;
        MainContext.getContext().setStage(primaryStage);
        stage = MainContext.getContext().getStage();
        stage.setMinWidth(MIN_WINDOW_WIDTH);
        stage.setMinHeight(MIN_WINDOW_HEIGHT);
        stage = primaryStage;
        stage.setTitle("Rogalik");
        stage.getIcons().add(new Image("/images/rogalik.png"));
        SceneSelector.goToLoginScreen();
        stage.show();
        stage.toFront();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
