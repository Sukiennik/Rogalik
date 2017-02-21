package pl.rogalik.client.controller.login;

import pl.rogalik.client.MainContext;
import pl.rogalik.client.security.Authenticator;
import pl.rogalik.client.utils.UserLoaderSaver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created 11.12.16.
 */
public class LoginController implements Initializable {

    @FXML
    TextField userID;
    @FXML
    PasswordField userPassword;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Label logMessage;
    @FXML
    HBox logoBox;
    @FXML
    ImageView logoView;

    private static MainContext context;
    private UserLoaderSaver userLoaderSaver;

    public void setAppContext(MainContext context) {
        this.context = context;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userLoaderSaver = new UserLoaderSaver();
        loginButton.defaultButtonProperty().bind(loginButton.focusedProperty());
        registerButton.defaultButtonProperty().bind(registerButton.focusedProperty());

        logoView.fitHeightProperty().bind(logoBox.heightProperty());

        userID.setOnKeyPressed( event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                userPassword.requestFocus();
            }
        });

        userPassword.setOnKeyPressed( event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                loginButton.requestFocus();
            }
        });
        Platform.runLater( () -> userID.requestFocus() );
        this.load();

    }
    @FXML
    public void loginButtonActionHandler() {
        if(!context.userLogging(userID.getText(), userPassword.getText())){
            logMessage.setTextFill(javafx.scene.paint.Paint.valueOf("red"));
            logMessage.setText("Niepoprawna nazwa bądź hasło!");
        }
    }
    @FXML
    public void registerButtonActionHandler() {
        if (Authenticator.isValid(userID.getText())) {
            if (!context.userRegister(userID.getText(), userPassword.getText())) {
                logMessage.setTextFill(javafx.scene.paint.Paint.valueOf("red"));
                logMessage.setText("Użytkownik o takiej nazwie już istnieje!");
            }
            else if(!userLoaderSaver.saveUsers(Authenticator.getUsers())){
                logMessage.setTextFill(javafx.scene.paint.Paint.valueOf("red"));
                logMessage.setText("Wystąpił błąd podczas rejestracji");
            }
            else{
                logMessage.setTextFill(javafx.scene.paint.Paint.valueOf("green"));
                logMessage.setText("Rejestracja została zakończona pomyślnie!");
            }
        }
        else {
            logMessage.setTextFill(javafx.scene.paint.Paint.valueOf("red"));
            logMessage.setText("Niepoprawna nazwa użytkownika!");
        }
    }

    private void load(){
        Authenticator.setUsers(this.userLoaderSaver.loadUsers());
        Authenticator.setAdmins(this.userLoaderSaver.loadAdmins());
    }


}
