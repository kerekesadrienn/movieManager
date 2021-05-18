package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class SidebarController implements Initializable{

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(SidebarController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUI("search");
    }

    @FXML
    private BorderPane borderPane;


    @FXML
    public void close() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
        log.info("Closed the app");
    }


    @FXML
    public void ui2() {
        loadUI("upload");
        log.info("Choose the upload scene");
    }

    @FXML
    public void ui3() {
        loadUI("search");
        log.info("Choose the search scene");
    }


    private void loadUI(String ui){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/" + ui + ".fxml"));
            log.info("Load the " + ui + " scene");
        } catch (IOException ex){
            log.warn("Couldn't load the scene " + ex);
        }
        borderPane.setCenter(root);
    }

}
