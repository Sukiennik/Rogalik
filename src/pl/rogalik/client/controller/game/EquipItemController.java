package pl.rogalik.client.controller.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pl.rogalik.client.model.Attribute;
import pl.rogalik.client.model.Item;

import java.util.Iterator;

public class EquipItemController {

    @FXML
    private ListView itemsView;
    @FXML
    private VBox attributesView;
    @FXML
    private VBox valuesView;
    @FXML
    private Button done;
    @FXML
    private Button cancel;

    private Stage dialogStage;
    private Item selectedItem;

    @FXML
    private void initialize(){

        done.defaultButtonProperty().bind(done.focusedProperty());
        cancel.defaultButtonProperty().bind(cancel.focusedProperty());

        itemsView.setOnKeyPressed(event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                dialogStage.hide();
            }
        });

        done.setOnKeyPressed(event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                dialogStage.hide();
            }
        });
        cancel.setOnKeyPressed(event -> {
            if( event.getCode().equals(KeyCode.ENTER)){
                selectedItem = null;
                dialogStage.hide();
            }
        });

        // cell factory for the items in listview items
        itemsView.setCellFactory(lv -> {
            TextFieldListCell<Item> cell = new TextFieldListCell<Item>();
            cell.setConverter(new StringConverter<Item>() {
                @Override
                public String toString(Item item) {
                    return item.getName();
                }
                @Override
                public Item fromString(String string) {
                    return cell.getItem() ;
                }
            });
            return cell;
        });

        itemsView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedItem = (Item)newValue;
            updateStats();
        });
        //TODO: Nie działa bo hero nie ma itemów, postponed
        /*
        ObservableList<Item> items = FXCollections.observableArrayList(MainContext.getContext().getChosenHero().getItems());
        itemsView.setItems(items);
        */
        Platform.runLater(() -> itemsView.getSelectionModel().selectFirst());

    }

    private void updateStats(){
        clearStats();

        Iterator<Attribute> attributes = selectedItem.getAttributes().iterator();
        Iterator<Node> labelNames = attributesView.getChildren().iterator();
        Iterator<Node> labelValues = valuesView.getChildren().iterator();
        while (attributes.hasNext() && labelNames.hasNext() && labelNames.hasNext()) { // zbędne dla label hasnext ale just in case
            Label labelName = (Label)labelNames.next();
            Label labelValue = (Label)labelValues.next();
            Attribute attribute = attributes.next();
            labelName.setText(attribute.getName());
            labelValue.setText(attribute.getValue().toString());
        }
    }

    private void clearStats(){
        for(Node node : attributesView.getChildren()){
            Label label = (Label)node;
            label.setText("");
        }
        for(Node node : valuesView.getChildren()){
            Label label = (Label)node;
            label.setText("");
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    Item getSelectedItem() {return selectedItem;}
}
