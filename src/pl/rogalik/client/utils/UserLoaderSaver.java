package pl.rogalik.client.utils;

import javafx.scene.shape.Path;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLoaderSaver {

    private final String SAVE_DIRECTORY = "saves";
    private final String USER_SAVE_FILE;
    private final String ADMIN_SAVE_FILE;

    public UserLoaderSaver(){

        this.USER_SAVE_FILE = String.join(File.separator, SAVE_DIRECTORY, "users.ser");
        this.ADMIN_SAVE_FILE = String.join(File.separator, SAVE_DIRECTORY, "admins.ser");
    }

    public boolean saveUsers(Map<String, String> users){
        try {
            Files.createDirectories(Paths.get(SAVE_DIRECTORY));
        } catch (IOException e) {
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_SAVE_FILE))) {
            oos.writeObject(users);
            return true;
        }catch(IOException e) {
            System.out.println("saveUser, IO except. " + e.getMessage());
            return false;
        }
    }

    public boolean saveAdmins(ArrayList<String> admins){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADMIN_SAVE_FILE))) {
            oos.writeObject(admins);
            return true;
        }catch(IOException e) {
            System.out.println("saveAdmin, IO except. " + e.getMessage());
            return false;
        }
    }

    public Map<String, String> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_SAVE_FILE))) {
            return ((Map<String, String>) ois.readObject());
        }catch(IOException | ClassNotFoundException e) {
            HashMap<String, String> users = new HashMap<String, String>();
            users.put("test", "test");
            return users;
        }
    }

    public ArrayList<String> loadAdmins() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ADMIN_SAVE_FILE))) {
            return ((ArrayList<String>) ois.readObject());
        }catch(IOException | ClassNotFoundException e) {
            ArrayList<String> admins = new ArrayList<String>();
            admins.add("test");
            return admins;
        }
    }
}
