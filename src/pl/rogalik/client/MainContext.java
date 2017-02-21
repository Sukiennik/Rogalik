package pl.rogalik.client;

import javafx.stage.Stage;
import pl.rogalik.client.model.User;
import pl.rogalik.client.security.Authenticator;
import pl.rogalik.client.utils.SceneSelector;
import pl.rogalik.objects.Hero;

/**
 * Created 11.12.16.
 */
    public class MainContext {

    private final static MainContext context = new MainContext();
    private Stage stage;
    private User loggedUser;
    private Hero chosenHero;

    public static MainContext getContext() {
        return context;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() { return stage; }

    public boolean userLogging(String userId, String userPassword){
        if(Authenticator.validate(userId, userPassword)) {
            loggedUser = User.ofId(userId);
            SceneSelector.goToMenuScreen();
            return true;
        } else {
            return false;
        }
    }

    public boolean userRegister(String userId, String userPassword){
        if(!Authenticator.userExists(userId)){
            Authenticator.addUser(userId, userPassword);
            return true;
        }
        else {
            return false;
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void userLoggingOut() {
        loggedUser = null;
        SceneSelector.goToLoginScreen();
    }

    public Hero getChosenHero(){ return  chosenHero;}
    public void setChosenHero(Hero hero){ this.chosenHero = hero;}

    public void startGame(){
        SceneSelector.goToGameScreen();
    }

    public void endGame(){
        this.chosenHero = null;
        SceneSelector.goToMenuScreen();
    }
}
