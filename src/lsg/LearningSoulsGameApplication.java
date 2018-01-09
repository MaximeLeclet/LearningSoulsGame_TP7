package lsg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.graphics.CSSFactory;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.texts.GameLabel;

public class LearningSoulsGameApplication extends Application {

    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Learning Souls Game");
        root = new AnchorPane();
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        buildUI();
        stage.show();
        startGame();

    }

    private void buildUI() {

        scene.getStylesheets().add(CSSFactory.getStyleSheet("LSG.css"));

        gameTitle = new TitlePane(scene, "Learning Souls Game");
        root.getChildren().addAll(gameTitle);
        root.setLeftAnchor(gameTitle, 0.0);
        root.setRightAnchor(gameTitle, 0.0);
        root.setTopAnchor(gameTitle, 0.0);

        creationPane = new CreationPane();
        root.setLeftAnchor(creationPane, 0.0);
        root.setRightAnchor(creationPane, 0.0);
        root.setTopAnchor(creationPane, 0.0);
        root.setBottomAnchor(creationPane, 0.0);
        root.getChildren().addAll(creationPane);

    }

    public void startGame() {

        gameTitle.zoomIn(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("ZOOM terminé");
            }
        });

    }

}
