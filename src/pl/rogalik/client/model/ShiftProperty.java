package pl.rogalik.client.model;

import javafx.scene.paint.Color;

/**
 * Created 07.01.17.
 */
@Deprecated
public class ShiftProperty
{
    private String textToShift;
    private Color textColorToShift;
    private Color backgroundColorToShift;

    public ShiftProperty(){}
    public ShiftProperty(String textToShift, Color textColorToShift, Color backgroundColorToShift){
        this.textToShift = textToShift;
        this.textColorToShift = textColorToShift;
        this.backgroundColorToShift = backgroundColorToShift;
    }
    public String getTextToShift() {
        return textToShift;
    }

    public void setTextToShift(String textToShift) {
        this.textToShift = textToShift;
    }

    public Color getTextColorToShift() {
        return textColorToShift;
    }

    public void setTextColorToShift(Color textColorToShift) {
        this.textColorToShift = textColorToShift;
    }

    public Color getBackgroundColorToShift() {
        return backgroundColorToShift;
    }

    public void setBackgroundColorToShift(Color backgroundColorToShift) {
        this.backgroundColorToShift = backgroundColorToShift;
    }
}
