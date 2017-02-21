package pl.rogalik.client.controller.menu;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import pl.rogalik.client.MainContext;
import pl.rogalik.client.security.Authenticator;
import pl.rogalik.client.utils.DialogManager;
import pl.rogalik.client.utils.HeroLoaderSaver;
import pl.rogalik.client.utils.SaveCleaner;
import pl.rogalik.client.utils.UserLoaderSaver;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;
import pl.rogalik.objects.Hero;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Label headingLabel;
    @FXML
    private Label heroNameLabel;
    @FXML
    private Label heroDamageLabel;
    @FXML
    private Label heroArmorLabel;
    @FXML
    private Label heroHpLabel;
    /*
    @FXML
    private Label heroEnergyLabel;
    */
    @FXML
    private TextArea menuLog;
    @FXML
    private ListView<Hero> listView;

    private MainContext context;
    private DialogManager dialogManager;
    private HeroLoaderSaver heroLoaderSaver;
    private Map<KeyCode, Runnable> viewListCommands;
    private Map<KeyCode, Runnable> windowCommands;

    public void setAppContext(MainContext context){
        this.context = context;
    }

    public void prepareFirstView(){

        headingLabel.setText(MainContext.getContext().getLoggedUser().getUserId());
        this.heroLoaderSaver = new HeroLoaderSaver(this.context.getLoggedUser().getUserId());
        this.context.getLoggedUser().setHeroes(heroLoaderSaver.loadHeroes());
        listView.setItems(getHeroes());
        listView.getSelectionModel().selectFirst();
        logMsg("Witaj w Rogaliku " + context.getLoggedUser().getUserId() + "! Wybierz swoją postać i szykuj się do gry");
        updateStats();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dialogManager = new DialogManager();
        listView.setCellFactory(lv -> {
            TextFieldListCell<Hero> cell = new TextFieldListCell<Hero>();
            cell.setConverter(new StringConverter<Hero>() {
                @Override
                public String toString(Hero hero) {
                    return hero.getName();
                }
                @Override
                public Hero fromString(String string) {
                    return cell.getItem() ;
                }
            });
            return cell;
        });

        Platform.runLater( () -> listView.requestFocus() );

        this.viewListCommands =  new HashMap<>();
        this.windowCommands = new HashMap<>();

        viewListCommands.put(KeyCode.DOWN, this::scrollDown);
        viewListCommands.put(KeyCode.UP, this::scrollUp);
        viewListCommands.put(KeyCode.D, this::deleteHero);

        windowCommands.put(KeyCode.ENTER, this::play);
        windowCommands.put(KeyCode.C, this::createHero);
        windowCommands.put(KeyCode.H, this::help);
        windowCommands.put(KeyCode.S, this::save);
        windowCommands.put(KeyCode.L, () -> context.userLoggingOut());
        windowCommands.put(KeyCode.Q, () -> context.getStage().close());
        //admin keys
        windowCommands.put(KeyCode.BACK_SPACE, this::showUsers);
        windowCommands.put(KeyCode.EQUALS, this::deleteUser);

        listView.setOnKeyPressed(event -> {
            if(windowCommands.containsKey(event.getCode())){
                windowCommands.get(event.getCode()).run();
            }
            if(viewListCommands.containsKey(event.getCode())){
                viewListCommands.get(event.getCode()).run();
                event.consume();
            }
        });
        menuLog.setOnKeyPressed(event -> {
            if(windowCommands.containsKey(event.getCode())){
                windowCommands.get(event.getCode()).run();
            }
        });

    }

    private Hero getSelectedHero(){
        return listView.getSelectionModel().getSelectedItem();
    }
    private ObservableList<Hero> getHeroes(){
        return this.context.getLoggedUser().getObservableHeroes();
    }
    private boolean isSelectedHero(){
        if(getSelectedHero() != null) {
            return true;
        }
        return false;
    }

    private void updateStats(){
        if(isSelectedHero()) {
            //logMsg("wybrałeś " + getSelectedHero().getName());
            heroNameLabel.setText(getSelectedHero().getName());
            heroDamageLabel.setText(Integer.toString(getSelectedHero().getDamage()));
            heroArmorLabel.setText(Integer.toString(getSelectedHero().getArmor()));
            heroHpLabel.setText(Integer.toString(getSelectedHero().getHp()));
            //heroEnergyLabel.setText(Integer.toString(hero.);
        }
    }

    private void scrollDown(){
        listView.getSelectionModel().selectNext();
        listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
        updateStats();
    }
    private void scrollUp(){
        listView.getSelectionModel().selectPrevious();
        listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
        updateStats();
    }

    private void deleteUser(){
        if(Authenticator.isAdmin(context.getLoggedUser().getUserId())) {
            dialogManager.runUserDeletionDialog();
            String username = ((CreateDeleteController)dialogManager.getController()).getNameField();
            if(Authenticator.userExists(username)) {
                Authenticator.removeUser(username);
                if(!(new SaveCleaner(username).deleteUser())){
                    logMsg("ERROR - usuwanie użytkownika nie podwiodło się");
                }
                logMsg("usunięto " + username);
                UserLoaderSaver userSaver = new UserLoaderSaver();
                userSaver.saveAdmins(Authenticator.getAdmins());
                userSaver.saveUsers(Authenticator.getUsers());
            }
            else{
                logMsg("Nie ma takiego użytkownika");
            }
        }
    }

    private void showUsers(){
        if(Authenticator.isAdmin(context.getLoggedUser().getUserId())) {
            logMsg("users:");
            for(HashMap.Entry<String, String> user : Authenticator.getUsers().entrySet()){
                logMsg(user.getKey());
            }
        }
    }

    private void play(){
        if(isSelectedHero()) {
            save();
            context.setChosenHero(getSelectedHero());
            context.startGame();
        }
    }

    private void createHero() {

        dialogManager.runHeroCreationDialog();
        String heroName = ((CreateDeleteController) dialogManager.getController()).getNameField();

        if(heroName == null || heroName.equals("")){
            return; // Validation
        }
        for (Hero hero : getHeroes()){
            if (hero.getName().equalsIgnoreCase(heroName)){
                logMsg("ERROR : taka postać już istnieje");
                return;
            }
        }

        // test
        Entity pos = new Entity(0,0, EntityType.HERO, 1);
        Hero hero = new Hero(pos , 1000, 10, 10, heroName);
        /*
        List<Item> items = new ArrayList<Item>();
        Item item = new Item("Miecz");
        item.getAttributes().add(new Attribute("Atak", 8));
        items.add(item);
        item = new Item("Zbroja");
        item.getAttributes().add(new Attribute("Obrona", 10));
        items.add(item);
        item = new Item("Buty");
        item.getAttributes().add(new Attribute("Życie", 50));
        item.getAttributes().add(new Attribute("Obrona", 3));
        items.add(item);
        item = new Item("Spodnie");
        item.getAttributes().add(new Attribute("Obrona", 7));
        item.getAttributes().add(new Attribute("Atak", 1));
        items.add(item);
        item = new Item("Tarcza");
        item.getAttributes().add(new Attribute("Obrona", 8));
        item.getAttributes().add(new Attribute("Atak", -2));
        items.add(item);
        hero.setItems(items);
        */
        context.getLoggedUser().addHero(hero);
        // test
        listView.getSelectionModel().select(hero);
        updateStats();
        logMsg("utworzono " + heroName);
        save();

    }

    private void deleteHero() {
        if(isSelectedHero()) {
            logMsg("usunięto " + getSelectedHero().getName());
            getHeroes().remove(listView.getSelectionModel().getSelectedIndex());
        }
    }

    private void save(){
        if(heroLoaderSaver.saveHeroes(context.getLoggedUser().getListHeroes())){
            logMsg("zapisano");
        }
        else {
            logMsg("ERROR - bład podczas zapisywania");
        }
    }

    private void help(){
        menuLog.appendText("enter - przenosi do gry wybraną postacią\n");
        menuLog.appendText("strzałki -  możesz wybrać gracza\n");
        menuLog.appendText("tab - przeskakujesz pomiędzy listą graczy a logami\n");
        menuLog.appendText("c - utwórz nową postać\n");
        menuLog.appendText("d - usuń postać obecnie wybraną\n");
        menuLog.appendText("h - wypisuje informacje o wszystkich komendach\n");
        menuLog.appendText("l - wylogowanie\n");
        menuLog.appendText("s - zapisuje\n");
        menuLog.appendText("q - wyłącza grę\n");
        if(Authenticator.isAdmin(context.getLoggedUser().getUserId())){
            menuLog.appendText("= - usuwa podanego użytkownika\n");
            menuLog.appendText("backspace - wypisuje użytkowników\n");
        }
    }

    private void logMsg(String msg){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        menuLog.appendText(msg + " - godzina " + dtf.format(now) + "\n");
    }
}



