package lsg.graphics.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lsg.graphics.widgets.texts.GameLabel;

public class CreationPane extends VBox {

    private TextField nameField;
    private GameLabel playerName;

    public CreationPane() {

        nameField = new TextField();
        nameField.setMaxWidth(200.0);
        playerName = new GameLabel("Player Name");
        this.getChildren().addAll(playerName);
        this.getChildren().addAll(nameField);
        this.setAlignment(Pos.CENTER);

    }

    public TextField getNameField() {
        return nameField;
    }

}
