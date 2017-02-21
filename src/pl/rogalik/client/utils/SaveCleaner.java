package pl.rogalik.client.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class SaveCleaner {

    private final String SAVE_DIRECTORY = "saves";
    private final String HEROES_DIRECTORY;
    private final String USER_FILE;
    private final String FILE_EXT = ".ser";

    public SaveCleaner(String name){
        this.HEROES_DIRECTORY = String.join(File.separator, SAVE_DIRECTORY, "heroes");
        this.USER_FILE = String.join(File.separator, HEROES_DIRECTORY, name + FILE_EXT);
    }

    public boolean deleteUser(){
        try {
            Files.deleteIfExists(FileSystems.getDefault().getPath(USER_FILE));
            return true;
        }
        catch ( IOException e) {
            return false;
        }
    }

}
