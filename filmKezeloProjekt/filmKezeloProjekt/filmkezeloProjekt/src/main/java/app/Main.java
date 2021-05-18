package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.LoggerFactory;


public class Main extends Application {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    private double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("The app is started ...");
        Parent root = FXMLLoader.load(getClass().getResource("/sidebar.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        Image close = new Image("/icon/Crystal_Project_Exit.png");
        ImageView piece = new ImageView(close);
        piece.setX(10);
        piece.setY(10);


        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);



    }

}

