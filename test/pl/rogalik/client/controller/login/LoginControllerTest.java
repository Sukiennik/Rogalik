package pl.rogalik.client.controller.login;

import pl.rogalik.client.MainContext;
import pl.rogalik.client.security.Authenticator;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created 11.01.17.
 */
@RunWith(pl.rogalik.client.JfxTestRunner.class)
public class LoginControllerTest{

    @Test
    public void loginButtonActionHandler() throws Exception {
        Map<String, String> USERS_MAP = new HashMap<>();
        USERS_MAP.put("login", "password");
        Authenticator.setUsers(USERS_MAP);

        TextField login = new TextField();
        login.setText("login");
        TextField password = new TextField();
        password.setText("password");
        assertTrue(MainContext.getContext().userLogging(login.getText(), password.getText()));
    }

    @Test
    public void registerButtonActionHandler() throws Exception {
        Map<String, String> USERS_MAP = new HashMap<>();
        USERS_MAP.put("login", "password");
        Authenticator.setUsers(USERS_MAP);

        assertTrue(Authenticator.isValid("login2") &&
                   MainContext.getContext().userRegister("login2", "password2"));
        assertFalse(MainContext.getContext().userRegister("login", "password"));
        assertFalse(Authenticator.isValid(""));
    }

}