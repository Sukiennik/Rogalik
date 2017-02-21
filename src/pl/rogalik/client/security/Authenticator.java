package pl.rogalik.client.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Authenticator {

    private static Map<String, String> USERS_MAP;
    private static ArrayList<String> ADMIN_LIST;

    public static boolean validate(String userId, String userPassword){
        String validUserPassword = USERS_MAP.get(userId.toLowerCase());
        return validUserPassword != null && validUserPassword.equals(userPassword);
    }

    public static boolean isValid(String userId){
        return (userId != null && !userId.equals(""));
    }

    public static Map<String, String> getUsers() {
        return USERS_MAP;
    }
    public static void setUsers(Map<String, String> userList) {
        Authenticator.USERS_MAP = userList;
    }
    public static void addUser(String user, String password){
        USERS_MAP.put(user.toLowerCase(),password);
    }
    public static void removeUser(String user){
        USERS_MAP.remove(user.toLowerCase());
        ADMIN_LIST.remove(user.toLowerCase());}

    public static boolean userExists(String user){
        return USERS_MAP.containsKey(user.toLowerCase());
    }

    public static void setAdmins(ArrayList<String> admins) {
        Authenticator.ADMIN_LIST = admins;
    }
    public static ArrayList<String> getAdmins() {
        return ADMIN_LIST;
    }
    public static void addAdmin(String user){
        ADMIN_LIST.add(user.toLowerCase());
    }
    public static boolean isAdmin(String user){ return ADMIN_LIST.contains(user.toLowerCase());}

}
