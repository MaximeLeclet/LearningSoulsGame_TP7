package lsg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.graphics.CSSFactory;

public class LearningSoulsGameApplication extends Application {

    Scene scene;
    AnchorPane root;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Learning Souls Game");
        root = new AnchorPane();
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        buildUI();
        stage.show();

    }

    private void buildUI() {

        scene.getStylesheets().add(CSSFactory.getStyleSheet("LSG.css"));

    }

}
