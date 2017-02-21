package pl.rogalik.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.rogalik.objects.Hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 11.12.16.
 */
public class User {

    private String userId;
    private static final Map<String, User> USERS_LIST = new HashMap<>();
    private ObservableList<Hero> heroes;

    private User(String userId) {
        this.userId = userId;
    }

    public static User ofId(String userId) {
        User user = USERS_LIST.get(userId.toLowerCase());
        if (user == null) {
            user = new User(userId);
            USERS_LIST.put(userId.toLowerCase(), user);
        }
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public ObservableList<Hero> getObservableHeroes() {
        return heroes;
    }
    public ArrayList<Hero> getListHeroes() {
        ArrayList<Hero> heroesList = new ArrayList<Hero>();
        for(Hero i : heroes) {
            heroesList.add(i);
        }
        return heroesList;
    }
    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = FXCollections.observableArrayList(heroes);
    }
    public boolean addHero(Hero hero){
        return heroes.add(hero);
    }
    public boolean removeHero(Hero hero){
        return heroes.remove(hero);
    }
}
