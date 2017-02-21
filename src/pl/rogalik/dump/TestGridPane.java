package pl.rogalik.dump;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created 11.12.16.
 */
public class TestGridPane extends Application {

    private static void setBackground(Region region, Color color) {
        region.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        RowConstraints rConstranits1 = new RowConstraints();
        rConstranits1.setVgrow(Priority.NEVER);
        RowConstraints rConstranits2 = new RowConstraints();
        rConstranits2.setVgrow(Priority.ALWAYS);
        RowConstraints rConstranits3 = new RowConstraints();
        rConstranits3.setVgrow(Priority.NEVER);

        ColumnConstraints cConstraints1 = new ColumnConstraints();
        cConstraints1.setHgrow(Priority.NEVER);
        ColumnConstraints cConstraints2 = new ColumnConstraints();
        cConstraints2.setHgrow(Priority.ALWAYS);
        ColumnConstraints cConstraints3 = new ColumnConstraints();
        cConstraints3.setHgrow(Priority.NEVER);

        gridPane.getColumnConstraints().addAll(cConstraints1, cConstraints2, cConstraints3);
        gridPane.getRowConstraints().addAll(rConstranits1, rConstranits2, rConstranits3);

        Region top = new Region();
        top.setPrefSize(300, 100);
        setBackground(top, Color.RED);

        Region bottom = new Region();
        bottom.setPrefSize(400, 50);
        setBackground(bottom, Color.YELLOW);


        Region center = new Region();
        setBackground(center, Color.BLUE);

        Region right = new Region();
        setBackground(right, Color.LIME);
        right.setPrefSize(200, 500);

        Region left = new Region();
        setBackground(left, Color.BROWN);
        left.setPrefSize(200, 400);

        gridPane.add(right, 2, 0, 1, 3);
        cConstraints3.maxWidthProperty().bind(right.prefWidthProperty());
        cConstraints3.minWidthProperty().bind(right.prefWidthProperty());
        gridPane.add(top, 0, 0, 2, 1);
        rConstranits1.minHeightProperty().bind(top.prefHeightProperty());
        rConstranits1.maxHeightProperty().bind(top.prefHeightProperty());
        gridPane.add(bottom, 0, 2, 2, 1);
        rConstranits3.minHeightProperty().bind(bottom.prefHeightProperty());
        rConstranits3.maxHeightProperty().bind(bottom.prefHeightProperty());
        gridPane.add(center, 1, 1);
        gridPane.add(left, 0, 1);
        cConstraints1.minWidthProperty().bind(left.prefWidthProperty());
        cConstraints1.maxWidthProperty().bind(left.prefWidthProperty());

        Scene scene = new Scene(gridPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
