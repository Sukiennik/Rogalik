package pl.rogalik.client.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created 12.12.16.
 */
public class MapNode extends StackPane {

    private String id;
    private int xPos, yPos;
    private Region background;
    private Color backgroundColor;
    private Label text;
    private Color textColor;

    public static class MapNodeBuilder {
        private String id;
        private int xPos;
        private int yPos;
        private Region background;
        private Color backgroundColor = Color.BLACK;
        private Label text;
        private Color textColor = Color.SILVER;

        public MapNodeBuilder(String id, int xPos, int yPos) {
            this.id = id;
            this.xPos = xPos;
            this.yPos = yPos;
            this.background = new Region();
            this.text = new Label(id);
            this.background.setBackground(new Background(
                    new BackgroundFill(backgroundColor,
                            CornerRadii.EMPTY,
                            Insets.EMPTY)));
            this.text.setTextFill(textColor);
        }

        public MapNodeBuilder background(Color backgroundColor)
        {
            this.backgroundColor = backgroundColor;
            this.background.setBackground(new Background(
                    new BackgroundFill(backgroundColor,
                            CornerRadii.EMPTY,
                            Insets.EMPTY)));
            return this;
        }
        public MapNodeBuilder textColor(Color textColor)
        {
            this.textColor = textColor;
            this.text.setTextFill(textColor);
            return this;
        }

        public MapNode build() {
            return new MapNode(this);
        }
    }

    private MapNode(MapNodeBuilder builder) {
        id = builder.id;
        xPos = builder.xPos;
        yPos = builder.yPos;
        background = builder.background;
        backgroundColor = builder.backgroundColor;
        text = builder.text;
        textColor = builder.textColor;

        MapNode.setAlignment(text, Pos.CENTER);
        this.getChildren().addAll(background, text);
        background.prefWidthProperty().bind(this.widthProperty());
        background.prefHeightProperty().bind(this.heightProperty());
    }

    public  void setNodeId(String id) {
        this.id = id;
        text.setText(id);
    }

    public String getNodeId() { return this.id; }

    public void setNodeBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.background.setBackground(new Background(
                new BackgroundFill(backgroundColor,
                        CornerRadii.EMPTY,
                        Insets.EMPTY)));
    }

    public Color getNodeBackgroundColor() { return this.backgroundColor; }

    public void setNodeTextColor(Color textColor) {
        this.textColor = textColor;
        this.text.setTextFill(textColor);
    }

    public Color getNodeTextColor() {
        return this.textColor;
    }

    public void setXPos(int x) {
        this.xPos = x;
    }

    public int getXPos() {
        return this.xPos;
    }

    public void setYPos(int y) {
        this.yPos = y;
    }

    public int getyPos() {
        return this.yPos;
    }


}
