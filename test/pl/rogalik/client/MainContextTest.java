package pl.rogalik.client;

import pl.rogalik.client.security.Authenticator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created 11.01.17.
 */
public class MainContextTest {
    @Test
    public void userLogging() throws Exception {
        Map<String, String> USERS_MAP = new HashMap<>();
        USERS_MAP.put("login", "password");
        Authenticator.setUsers(USERS_MAP);
        assertTrue(Authenticator.validate("login", "password"));
        assertFalse(Authenticator.validate("login2", "password2"));
    }

    @Test
    public void userRegister() throws Exception {
        Map<String, String> USERS_MAP = new HashMap<>();
        USERS_MAP.put("login", "password");
        Authenticator.setUsers(USERS_MAP);
        assertTrue(Authenticator.userExists("login"));
        assertTrue(!Authenticator.userExists("login2"));
    }

}