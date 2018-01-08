package lsg.graphics.widgets.texts;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class GameLabel extends Label {

    public GameLabel() {

        super();

    }

    public GameLabel(String text) {

        super(text);

    }

    public GameLabel(String text, Node graphic) {

        super(text, graphic);//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    }

}
