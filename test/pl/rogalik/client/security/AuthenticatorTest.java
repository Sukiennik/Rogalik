package pl.rogalik.client.security;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created 11.01.17.
 */
public class AuthenticatorTest {
    @Test
    public void validate() throws Exception {
        Map<String, String> USERS_MAP = new HashMap<>();
        USERS_MAP.put("login", "password");
        USERS_MAP.put("login2", "password2");
        String validPassword = USERS_MAP.get("login");

        assertTrue(validPassword.equals("password"));
        validPassword = USERS_MAP.get("login2");
        assertFalse(!validPassword.equals("password2"));
        validPassword = USERS_MAP.get("login3");
        assertTrue(validPassword == null);
    }

    @Test
    public void isValid() throws Exception {
        String userID = "userId";
        assertTrue(userID != null && !userID.equals(""));
        userID = "";
        assertFalse(userID != null && !userID.equals(""));
        userID = null;
        assertFalse(userID != null && !userID.equals(""));

    }

    @Test
    public void userExists() throws Exception {
        Map<String, String> USERS_MAP = new HashMap<>();
        USERS_MAP.put("login", "password");
        String user = "login";
        assertTrue(USERS_MAP.containsKey(user));
        user = "login2";
        assertFalse(USERS_MAP.containsKey(user));
    }

}