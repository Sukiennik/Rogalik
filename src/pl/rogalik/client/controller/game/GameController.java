package pl.rogalik.client.controller.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import pl.rogalik.client.MainContext;
import pl.rogalik.client.security.Authenticator;
import pl.rogalik.client.utils.DialogManager;
import pl.rogalik.client.utils.HeroLoaderSaver;
import pl.rogalik.client.utils.MapManager;
import pl.rogalik.client.utils.SceneSelector;
import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Hero;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.*;

/**
 * Created 11.12.16.
 */
public class GameController implements Initializable {
    @FXML
    private GridPane mapGrid;
    @FXML
    private TextArea gameLog;
    @FXML
    private Label headingLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label damageLabel;
    @FXML
    private Label armorLabel;
    @FXML
    private Label hpLabel;

    private DialogManager dialogManager;
    private MainContext context;
    private GameMap gameMap;
    private Map<KeyCode, Runnable> gameControlsList = new HashMap<>();
    private MapManager mapManager;
    private GameEngine engine;
    private Hero hero;
    private HeroLoaderSaver heroLoaderSaver;

    public void setAppContext(MainContext context) {
        this.context = context;
        this.engine.setHero(context.getChosenHero());
        this.hero = context.getChosenHero();
    }

    public void prepareFirstView(){
        updateStats(context.getChosenHero());
        this.heroLoaderSaver = new HeroLoaderSaver(context.getLoggedUser().getUserId());
        if(gameMap.getHeroPosition().getEntity().isPresent()){
            context.getChosenHero().setEntity(gameMap.getHeroPosition().getEntity().get());
        }
        else {
            logMsg("Coś poszło nie tak, błąd generowania mapy");
        }
        logMsg("Szykuj się do walki " + context.getChosenHero().getName() + "!");
    }

    public  GameController(){
        try {
            this.engine = new GameEngine();
        } catch (FileNotFoundException | ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        gameMap = engine.getGameMap();
    }

    private void changeMap(){
        context.startGame();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogManager = new DialogManager();
        mapManager = new MapManager(mapGrid, gameMap);
        mapManager.loadInitialMap();

        headingLabel.setText(MainContext.getContext().getChosenHero().getName());

        Platform.runLater(() -> gameLog.requestFocus());
        gameControlsList.put(K, this::changeMap);
        gameControlsList.put(NUMPAD1, this::actionMoveSW);
        gameControlsList.put(NUMPAD2, this::actionMoveS);
        gameControlsList.put(NUMPAD3, this::actionMoveSE);
        gameControlsList.put(NUMPAD4, this::actionMoveW);
        gameControlsList.put(NUMPAD5, this::actionAttack);
        gameControlsList.put(NUMPAD6, this::actionMoveE);
        gameControlsList.put(NUMPAD7, this::actionMoveNW);
        gameControlsList.put(NUMPAD8, this::actionMoveN);
        gameControlsList.put(NUMPAD9, this::actionMoveNE);
        gameControlsList.put(D, this::actionGameOver);
        gameControlsList.put(H, this::actionHelp);
        gameControlsList.put(L, this::actionSwitchHero);
        gameControlsList.put(S, this::actionSave);
        gameControlsList.put(Q, this::actionEndGame);
        gameLog.setOnKeyPressed(event -> {
            if(gameControlsList.containsKey(event.getCode())) {
                gameControlsList.get(event.getCode()).run();
                event.consume();
            }
            try {
                if (context.getChosenHero().getHp() <= 0) {
                    actionGameOver();
                }
                updateStats(context.getChosenHero());
            }catch (NullPointerException e){}
        });
    }

    private void actionSwitchHero() {
        context.endGame();
    }

    private void actionEndGame() { this.context.getStage().close(); }

    private void actionGameOver() {
        dialogManager.runGameOverDialog();
        context.getLoggedUser().removeHero(context.getChosenHero());
        actionSave();
        if(((GameOverController)dialogManager.getController()).getIsStillPlaying()){
            SceneSelector.goToMenuScreen();
        }
        else{
            context.userLoggingOut();
            SceneSelector.goToLoginScreen();
        }
    }

    private void actionMoveNE() {
        if((getPlayerXpos() != 0 | getPlayerYpos() != mapManager.getMapWidth()) && engine.moveHero(Direction.NE)) {
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveN() {
        if(getPlayerXpos()!=0 && engine.moveHero(Direction.N)){
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveNW() {
        if((getPlayerXpos() != 0 | getPlayerYpos() != 0) && engine.moveHero(Direction.NW)){
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveE() {
        if(getPlayerYpos()!=mapManager.getMapWidth() && engine.moveHero(Direction.E)){
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveW() {
        if(getPlayerYpos()!=0 && engine.moveHero(Direction.W)){
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveSE() {
        if((getPlayerXpos() != mapManager.getMapHeight() | getPlayerYpos() != mapManager.getMapWidth()) && engine.moveHero(Direction.SE))
        {
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveS() {
        if(getPlayerXpos()!=mapManager.getMapHeight() && engine.moveHero(Direction.S)){
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionMoveSW() {
        if((getPlayerXpos() != mapManager.getMapHeight() | getPlayerYpos() != 0) && engine.moveHero(Direction.SW)) {
            mapManager.repaintMap();
            logMsg("X/Y : "+getPlayerXpos()+"/"+getPlayerYpos());
        }
    }

    private void actionAttack() {
        for(Direction e: Direction.values()){
            if(engine.attack(e)) {
                logMsg("Attack successful!");
                break;
            }
        }
        mapManager.repaintMap();
        updateStats(hero);
    }

    private void actionHelp(){
        gameLog.appendText("1,2,3,4,6,7,8,9 na klawiaturze numerycznej możesz się porusząc we wszystkich kierunkach świata, ruchy po skosie kosztują jednak więcej energii\n");
        gameLog.appendText("5 - atak\n");
        gameLog.appendText("d - porażka\n");
        gameLog.appendText("h - wypisuje informacje o wszystkich komendach\n");
        gameLog.appendText("l - przenosi cię do okna wybrania postaci\n");
        gameLog.appendText("q - wyłącza grę\n");
        if(Authenticator.isAdmin(context.getLoggedUser().getUserId())){
            //TODO: Admin actions if admins in game
        }
    }

    private void actionSave(){
        if(heroLoaderSaver.saveHeroes(context.getLoggedUser().getListHeroes())){
            logMsg("Zapisano");
        }
        else {
            logMsg("ERROR - bład podczas zapisywania");
        }
    }

    private int getPlayerXpos() {
        return this.gameMap.getHeroPosition().getEntity().get().getxPosition();
    }

    private int getPlayerYpos() {
        return this.gameMap.getHeroPosition().getEntity().get().getyPosition();
    }


    private void logMsg(String msg){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        gameLog.appendText(dtf.format(now) + " " + msg + "\n");
    }

    private void updateStats(Hero hero){
        nameLabel.setText(hero.getName());
        damageLabel.setText(Integer.toString(hero.getDamage()));
        armorLabel.setText(Integer.toString(hero.getArmor()));
        hpLabel.setText(Integer.toString(hero.getHp()));
    }
}

