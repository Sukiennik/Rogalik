package pl.rogalik.client.utils;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.rogalik.client.model.MapNode;
import pl.rogalik.client.model.ShiftProperty;
import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;

/**
 * Created 14.01.17.
 */
public final class MapManager {
    private GridPane mapGrid;
    private GameMap gameMap;
    private static MapNode[][] screenMap = new MapNode[33][33];
    private static MapNode underPlayerNode = new MapNode.MapNodeBuilder("",0,0).textColor(Color.SILVER).background(Color.BLACK).build();
    private int xOffset = 16;
    private int yOffset = 16;
    private int mapWidth;
    private int mapHeight;

    public MapManager(GridPane mapGrid, GameMap gameMap) {
        this.mapGrid = mapGrid;
        this.mapGrid.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), null, null)));
        this.gameMap = gameMap;
        this.mapWidth = this.gameMap.getMap()[0].length;
        this.mapHeight = this.gameMap.getMap().length;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void repaintMap(){
        if(getPlayerXpos() < xOffset && getPlayerYpos() < yOffset) {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[i][j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[i][j]);
                }
            } // 2
        } else if(getPlayerXpos() < xOffset && getPlayerYpos() > (gameMap.getMap()[0].length - 1 - yOffset)) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[i][gameMap.getMap()[0].length - screenMap[0].length + j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[i][gameMap.getMap()[0].length - screenMap[0].length + j]);
                }
            } // 6
        } else if(getPlayerXpos() < xOffset) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[i][getPlayerYpos()-yOffset+j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[i][getPlayerYpos()-yOffset+j]);
                }
            } // 4
        } else if(getPlayerXpos() > gameMap.getMap().length - 1 - xOffset && getPlayerYpos() < yOffset) {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][j]);
                }
            } // 3
        } else if(getPlayerXpos() > gameMap.getMap().length - 1 - xOffset && getPlayerYpos() > gameMap.getMap()[0].length - 1 - yOffset) {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][gameMap.getMap()[0].length-screenMap[0].length+j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][gameMap.getMap()[0].length-screenMap[0].length+j]);
                }
            } // 8
        } else if(getPlayerXpos() > gameMap.getMap().length - 1 - xOffset) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][getPlayerYpos()-yOffset+j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][getPlayerYpos()-yOffset+j]);
                }
            } // 5
        } else if(getPlayerYpos() < yOffset ) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[getPlayerXpos()-xOffset+i][j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[getPlayerXpos()-xOffset+i][j]);
                }
            } // 7
        } else if(getPlayerYpos() > gameMap.getMap()[0].length - 1 - yOffset) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[getPlayerXpos()-xOffset+i][gameMap.getMap()[0].length-screenMap[0].length+j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[getPlayerXpos()-xOffset+i][gameMap.getMap()[0].length-screenMap[0].length+j]);
                }
            } // 9
        } else {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    screenMap[i][j].setNodeId(gameMap.getMap()[getPlayerXpos()-xOffset+i][getPlayerYpos()-yOffset+j].toString());
                    updateColor(screenMap[i][j], gameMap.getMap()[getPlayerXpos()-xOffset+i][getPlayerYpos()-yOffset+j]);
                }
            }
        }
        repaintPlayer(screenMap[calibratePlayerXpos()][calibratePlayerYpos()]);
    }

    private void repaintPlayer(MapNode playerNode){
        playerNode.setNodeId("@");
        playerNode.setNodeTextColor(Color.CYAN);
    }

    private void updateColor(MapNode node, Tile tile){
        if(tile.isWall()) node.setNodeTextColor(Color.BROWN);
        else if(tile.getEntity().isPresent()) {
            if (tile.getEntity().get().getType().isCharacterType()) node.setNodeTextColor(Color.PURPLE);
            else if (tile.getEntity().get().getType().isItemType()) node.setNodeTextColor(Color.YELLOW);
        }
        else node.setNodeTextColor(Color.DARKGREEN);
    }

    public void loadInitialMap(){
        /*
        MAPA
        ###|######|###
        #1.|..6...|.2#
        #..|......|..#
        --------------
        #..|......|..#
        #5.|..9...|.7#
        #..|......|..#
        --------------
        #..|......|..#
        #4.|..8...|.3#
        ###|######|###
         */
        // 1
        if(getPlayerXpos() < xOffset && getPlayerYpos() < yOffset) {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[i][j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[i][j]);
                    mapGrid.add(node, j, i);
                }
            } // 2
        } else if(getPlayerXpos() < xOffset && getPlayerYpos() > (gameMap.getMap()[0].length - 1 - yOffset)) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[i][gameMap.getMap()[0].length - screenMap[0].length + j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[i][gameMap.getMap()[0].length - screenMap[0].length + j]);
                    mapGrid.add(node, j, i);
                }
            } // 6
        } else if(getPlayerXpos() < xOffset) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[i][getPlayerYpos()-yOffset+j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[i][getPlayerYpos()-yOffset+j]);
                    mapGrid.add(node, j, i);
                }
            } // 4
        } else if(getPlayerXpos() > gameMap.getMap().length - 1 - xOffset && getPlayerYpos() < yOffset) {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][j]);
                    mapGrid.add(node, j, i);
                }
            } // 3
        } else if(getPlayerXpos() > gameMap.getMap().length - 1 - xOffset && getPlayerYpos() > gameMap.getMap()[0].length - 1 - yOffset) {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][gameMap.getMap()[0].length-screenMap[0].length+j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][gameMap.getMap()[0].length-screenMap[0].length+j]);
                    mapGrid.add(node, j, i);
                }
            } // 8
        } else if(getPlayerXpos() > gameMap.getMap().length - 1 - xOffset) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][getPlayerYpos()-yOffset+j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[gameMap.getMap().length-screenMap.length+i][getPlayerYpos()-yOffset+j]);
                    mapGrid.add(node, j, i);
                }
            } // 5
        } else if(getPlayerYpos() < yOffset ) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[getPlayerXpos()-xOffset+i][j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[getPlayerXpos()-xOffset+i][j]);
                    mapGrid.add(node, j, i);
                }
            } // 7
        } else if(getPlayerYpos() > gameMap.getMap()[0].length - 1 - yOffset) {
            for (int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(gameMap.getMap()[getPlayerXpos()-xOffset+i][gameMap.getMap()[0].length-screenMap[0].length+j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[getPlayerXpos()-xOffset+i][gameMap.getMap()[0].length-screenMap[0].length+j]);
                    mapGrid.add(node, j, i);
                }
            } // 9
        } else {
            for(int i = 0; i < screenMap.length; i++) {
                for (int j = 0; j < screenMap[0].length; j++) {
                    MapNode node = new MapNode.MapNodeBuilder(
                            gameMap.getMap()[getPlayerXpos()-xOffset+i][getPlayerYpos()-yOffset+j].toString(), i, j)
                            .build();
                    screenMap[i][j] = node;
                    updateColor(screenMap[i][j], gameMap.getMap()[getPlayerXpos()-xOffset+i][getPlayerYpos()-yOffset+j]);
                    mapGrid.add(node, j, i);
                }
            }
        }
        setPlayerInitialLocation(screenMap[calibratePlayerXpos()][calibratePlayerYpos()]);
    }

    private void setPlayerInitialLocation(MapNode playerNode){
        playerNode.setNodeTextColor(Color.CYAN);
        playerNode.setNodeId("@");
    }

    @Deprecated
    public void moveUp() {
        // obszar pomiedzy offsetami
        if(getPlayerXpos() < gameMap.getMap().length - xOffset && getPlayerXpos() > xOffset) {
            shiftMapUp();
        } //
        else {
            movePlayer(calibratePlayerXpos(), calibratePlayerYpos(), calibratePlayerXpos()-1, calibratePlayerYpos()); // up
        }
    }

    @Deprecated
    public void moveDown() {
        if (getPlayerXpos() >= xOffset && getPlayerXpos() < gameMap.getMap().length - xOffset - 1) {
            shiftMapDown();
        } else {
            movePlayer(calibratePlayerXpos(), calibratePlayerYpos(), calibratePlayerXpos()+1, calibratePlayerYpos()); // down
        }
    }

    @Deprecated
    public void moveLeft() {
        if(getPlayerYpos() < gameMap.getMap()[0].length - yOffset && getPlayerYpos() > yOffset) {
            shiftMapLeft();
        } else {
            movePlayer(calibratePlayerXpos(), calibratePlayerYpos(), calibratePlayerXpos(), calibratePlayerYpos()-1); // left
        }
    }

    @Deprecated
    public void moveRight() {
        if (getPlayerYpos() >= yOffset && getPlayerYpos() < gameMap.getMap()[0].length - yOffset - 1) {
            shiftMapRight();
        } else {
            movePlayer(calibratePlayerXpos(), calibratePlayerYpos(), calibratePlayerXpos(), calibratePlayerYpos()+1); // right
        }
    }

    @Deprecated
    private void shiftMapUp() {
        ShiftProperty[] readShiftBuffer = new ShiftProperty[33];
        ShiftProperty[] writeShiftBuffer = new ShiftProperty[33];
        for(int i = 0; i < screenMap.length; i++) {
            readShiftBuffer[i] = new ShiftProperty(screenMap[0][i].getNodeId(),
                    screenMap[0][i].getNodeTextColor(),
                    screenMap[0][i].getNodeBackgroundColor());
            writeShiftBuffer[i] = new ShiftProperty();
        }
        for(int j = 0; j < screenMap[0].length; j++) {
            if(getPlayerYpos() < yOffset) {
                screenMap[0][j].setNodeId(String.valueOf(gameMap.getMap()[getPlayerXpos()-xOffset-1][j]));
            } else if (getPlayerYpos() > gameMap.getMap()[0].length - 1 - yOffset) {
                screenMap[0][j].setNodeId(gameMap.getMap()[getPlayerXpos()-xOffset-1][gameMap.getMap()[0].length-screenMap[0].length+j].toString());
            } else
                screenMap[0][j].setNodeId(gameMap.getMap()[getPlayerXpos()-xOffset-1][getPlayerYpos()-yOffset+j].toString());
        }
        shiftUpForward(screenMap, readShiftBuffer, writeShiftBuffer);
    }

    @Deprecated
    private void shiftMapDown() {
        ShiftProperty readShiftedProperties[] = new ShiftProperty[33];
        ShiftProperty writeShiftedProperties[] = new ShiftProperty[33];
        for(int i = 0; i < screenMap.length; i++) {
            readShiftedProperties[i] = new ShiftProperty(screenMap[32][i].getNodeId(),
                    screenMap[32][i].getNodeTextColor(),
                    screenMap[32][i].getNodeBackgroundColor());
            writeShiftedProperties[i] = new ShiftProperty();
        }

        for(int j = 0; j < screenMap[0].length; j++) {
            if(getPlayerYpos() < yOffset) {
                screenMap[32][j].setNodeId(gameMap.getMap()[getPlayerXpos()+xOffset+1][j].toString());
            } else if (getPlayerYpos() > gameMap.getMap().length - 1 - yOffset) {
                screenMap[32][j].setNodeId(gameMap.getMap()[getPlayerXpos()+xOffset+1][gameMap.getMap()[0].length-1-32+j].toString());
            } else
                screenMap[32][j].setNodeId(gameMap.getMap()[getPlayerXpos()+xOffset+1][getPlayerYpos()-yOffset+j].toString());
        }
        shiftDownBackward(screenMap, readShiftedProperties, writeShiftedProperties);
    }

    @Deprecated
    private void shiftMapLeft() {
        ShiftProperty readShiftBuffer[] = new ShiftProperty[33];
        ShiftProperty writeShiftBuffer[] = new ShiftProperty[33];
        for (int i = 0; i < screenMap.length; i++) {
            readShiftBuffer[i] = new ShiftProperty(screenMap[i][0].getNodeId(),
                    screenMap[i][0].getNodeTextColor(),
                    screenMap[i][0].getNodeBackgroundColor());
            writeShiftBuffer[i] = new ShiftProperty();
        }
        for (int j = 0; j < screenMap.length; j++) {
            if (getPlayerXpos() < xOffset) {
                screenMap[j][0].setNodeId(gameMap.getMap()[j][getPlayerYpos() - yOffset - 1].toString());
            } else if (getPlayerXpos() > gameMap.getMap().length - 1 - xOffset) {
                screenMap[j][0].setNodeId(gameMap.getMap()[gameMap.getMap().length - screenMap.length + j][getPlayerYpos() - yOffset - 1].toString());
            } else
                screenMap[j][0].setNodeId(gameMap.getMap()[getPlayerXpos() - xOffset + j][getPlayerYpos() - yOffset - 1].toString());
        }
        shiftLeftForward(screenMap, readShiftBuffer, writeShiftBuffer);
    }

    @Deprecated
    private void shiftMapRight() {
        ShiftProperty readShiftBuffer[] = new ShiftProperty[33];
        ShiftProperty writeShiftBuffer[] = new ShiftProperty[33];
        for (int i = 0; i < screenMap.length; i++) {
            readShiftBuffer[i] = new ShiftProperty(screenMap[i][32].getNodeId(),
                    screenMap[i][32].getNodeTextColor(),
                    screenMap[i][32].getNodeBackgroundColor());
            writeShiftBuffer[i] = new ShiftProperty();
        }
        for (int j = 0; j < screenMap.length; j++) {
            if (getPlayerXpos() < xOffset) {
                screenMap[j][32].setNodeId(String.valueOf(gameMap.getMap()[j][getPlayerYpos() + yOffset + 1]));
            } else if (getPlayerXpos() > gameMap.getMap().length - 1 - xOffset) {
                screenMap[j][32].setNodeId(gameMap.getMap()[gameMap.getMap().length - screenMap.length + j][getPlayerYpos() + yOffset + 1].toString());
            } else
                screenMap[j][32].setNodeId(gameMap.getMap()[getPlayerXpos() - xOffset + j][getPlayerYpos() + yOffset + 1].toString());
        }
        shiftRightBackward(screenMap, readShiftBuffer, writeShiftBuffer);
    }

    @Deprecated
    @SuppressWarnings("Duplicates")
    private void shiftUpForward(MapNode[][] screenMap, ShiftProperty[] readShiftProperties, ShiftProperty[] writeShiftProperties){
        for(int i = 1; i < screenMap.length; i++) {
            for (int j = 0; j < screenMap[0].length; j++) {
                if (i == calibratePlayerXpos() && j == calibratePlayerYpos()) {
                    shiftNode(readShiftProperties[j], writeShiftProperties[j], underPlayerNode);
                    continue;
                }
                shiftNode(readShiftProperties[j], writeShiftProperties[j], screenMap[i][j]);
            }
        }
    }

    @Deprecated
    @SuppressWarnings("Duplicates")
    private void shiftDownBackward(MapNode[][] screenMap, ShiftProperty[] readShiftProperties, ShiftProperty[] writeShiftProperties) {
        for(int i = screenMap.length-2; i >= 0; i--) {
            for (int j = 0; j < screenMap[0].length; j++) {
                if( i == calibratePlayerXpos() && j == calibratePlayerYpos() ) {
                    shiftNode(readShiftProperties[j], writeShiftProperties[j], underPlayerNode);
                    continue;
                }
                shiftNode(readShiftProperties[j], writeShiftProperties[j], screenMap[i][j]);
            }
        }
    }

    @Deprecated
    @SuppressWarnings("Duplicates")
    private void shiftLeftForward(MapNode[][] screenMap, ShiftProperty[] readShiftProperties, ShiftProperty[] writeShiftProperties) {
        for (int i = 1; i < screenMap[0].length; i++) {
            for (int j = 0; j < screenMap.length; j++) {
                if( j == calibratePlayerXpos() && i == calibratePlayerYpos() ) {
                    shiftNode(readShiftProperties[j], writeShiftProperties[j], underPlayerNode);
                    continue;
                }
                shiftNode(readShiftProperties[j], writeShiftProperties[j], screenMap[j][i]);
            }
        }
    }

    @Deprecated
    @SuppressWarnings("Duplicates")
    private void shiftRightBackward(MapNode[][] screenMap, ShiftProperty[] readShiftProperties, ShiftProperty[] writeShiftProperties){
        for (int i = screenMap.length-2; i >= 0; i--) {
            for (int j = 0; j < screenMap[0].length; j++) {
                if( j == calibratePlayerXpos() && i == calibratePlayerYpos() ) {
                    shiftNode(readShiftProperties[j], writeShiftProperties[j], underPlayerNode);
                    continue;
                }
                shiftNode(readShiftProperties[j], writeShiftProperties[j], screenMap[j][i]);
            }
        }
    }

    @Deprecated
    private void shiftNode(ShiftProperty readShiftProperty, ShiftProperty writeShiftProperty, MapNode nodeToShift) {
        writeShiftProperty.setTextToShift(nodeToShift.getNodeId());
        writeShiftProperty.setTextColorToShift(nodeToShift.getNodeTextColor());
        writeShiftProperty.setBackgroundColorToShift(nodeToShift.getNodeBackgroundColor());
        nodeToShift.setNodeId(readShiftProperty.getTextToShift());
        nodeToShift.setNodeTextColor(readShiftProperty.getTextColorToShift());
        nodeToShift.setNodeBackgroundColor(readShiftProperty.getBackgroundColorToShift());
        readShiftProperty.setTextToShift(writeShiftProperty.getTextToShift());
        readShiftProperty.setTextColorToShift(writeShiftProperty.getTextColorToShift());
        readShiftProperty.setBackgroundColorToShift(writeShiftProperty.getBackgroundColorToShift());
    }

    @Deprecated
    private void movePlayer(int x, int y, int toX, int toY) {
        MapNode player = screenMap[x][y];//getNodeFromGridPane(x, y);
        MapNode direction = screenMap[toX][toY];//getNodeFromGridPane(toX, toY);

        ShiftProperty tmp = new ShiftProperty(player.getNodeId(), player.getNodeTextColor(), player.getNodeBackgroundColor());
        swapNodeProperties(player, underPlayerNode);
        underPlayerNode.setNodeId(direction.getNodeId());
        underPlayerNode.setNodeTextColor(direction.getNodeTextColor());
        underPlayerNode.setNodeBackgroundColor(direction.getNodeBackgroundColor());
        direction.setNodeId(tmp.getTextToShift());
        direction.setNodeTextColor(tmp.getTextColorToShift());
        direction.setNodeBackgroundColor(tmp.getBackgroundColorToShift());
    }

    @Deprecated
    private void swapNodeProperties(MapNode first, MapNode second) {
        ShiftProperty tmp = new ShiftProperty(first.getNodeId(), first.getNodeTextColor(), first.getNodeBackgroundColor());
        first.setNodeId(second.getNodeId());
        first.setNodeTextColor(second.getNodeTextColor());
        first.setNodeBackgroundColor(second.getNodeBackgroundColor());
        second.setNodeId(tmp.getTextToShift());
        second.setNodeTextColor(tmp.getTextColorToShift());
        second.setNodeBackgroundColor(tmp.getBackgroundColorToShift());
    }

    private int calibratePlayerXpos(){
        if(getPlayerXpos() < xOffset)
            return getPlayerXpos();
        else if (getPlayerXpos() > gameMap.getMap().length - 1 - xOffset)
            return Math.abs(gameMap.getMap().length-screenMap.length-getPlayerXpos());
        else
            return xOffset;
    }

    private int calibratePlayerYpos(){
        if(getPlayerYpos() < yOffset)
            return getPlayerYpos();
        else if (getPlayerYpos() > gameMap.getMap()[0].length - 1 - yOffset)
            return Math.abs(gameMap.getMap()[0].length-screenMap[0].length-getPlayerYpos());
        else
            return yOffset;
    }

    private int getPlayerXpos() {
        return this.gameMap.getHeroPosition().getEntity().get().getxPosition();
    }

    private int getPlayerYpos() {
        return this.gameMap.getHeroPosition().getEntity().get().getyPosition();
    }

}
