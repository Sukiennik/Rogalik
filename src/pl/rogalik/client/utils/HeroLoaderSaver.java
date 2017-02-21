package pl.rogalik.client.utils;


import pl.rogalik.objects.Hero;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HeroLoaderSaver {

    private final String SAVE_FILE;
    private final String SAVE_DIRECTORY = "saves";
    private final String HERO_DIRECTORY;

    public HeroLoaderSaver(String filename){
        this.HERO_DIRECTORY = String.join(File.separator, SAVE_DIRECTORY, "heroes");
        this.SAVE_FILE = String.join(File.separator, HERO_DIRECTORY, filename + ".ser");
    }

    public boolean saveHeroes(ArrayList<Hero> heroes){
        try {
            Files.createDirectories(Paths.get(HERO_DIRECTORY));
        } catch (IOException e) {
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE));){
            oos.writeObject(heroes);
            return true;
        }catch(IOException e) {
            return false;
        }
    }

    public ArrayList<Hero> loadHeroes() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return ((ArrayList<Hero>) ois.readObject());
        }catch(IOException | ClassNotFoundException e) {
            return new ArrayList<Hero>();
        }
    }
}
