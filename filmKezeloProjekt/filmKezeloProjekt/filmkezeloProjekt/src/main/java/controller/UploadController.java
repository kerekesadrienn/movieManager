package controller;

import dao.MovieDAO;
import dao.impl.MovieImpl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;
import org.slf4j.LoggerFactory;
import service.MovieService;
import service.MovieServiceImpl;
import utility.DBManager;

import java.util.ArrayList;
import java.util.List;


public class UploadController {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(UploadController.class);

    @FXML
    public Label warningMessage;

    @FXML
    public ComboBox<Category> categoryComboBox;

    @FXML
    private TextArea movieDescriptionField;

    @FXML
    private TextField movieNameField;

    @FXML
    private VBox pane_main_grid;

    @FXML
    private Pane capitalpane;

    @FXML
    private Label message;

    private HBox hBox;
    private TextField newField;
    private String actorName;
    private String typeName;
    private int count = 0;
    private boolean isExist = false;

    private MovieDAO movieDAO = new MovieImpl(DBManager.getInstance());
    private MovieService movieServiceDAO = new MovieServiceImpl(movieDAO);


    public void initialize(){
        categoryComboBox.getItems().addAll(
                Category.VÍGJÁTÉK,
                Category.DRÁMA,
                Category.THRILLER,
                Category.HORROR,
                Category.AKCIÓ,
                Category.ROMANTIKUS,
                Category.SCIFI,
                Category.HORROR,
                Category.DOKUMENTUMFILM
        );
        categoryComboBox.setValue(Category.VÍGJÁTÉK);
    }


    @FXML
    private void handleButton1Action() {

        boolean empty = false;

        for (Node node : capitalpane.getChildren()) {
            if (node instanceof TextArea) {
                if (((TextArea) node).getText().trim().isEmpty()) {
                    log.warn("Empty description field");
                    message.setText("Kérlek töltsd ki a mezőket");
                    message.setTextFill(Color.RED);
                    empty = true;
                    break;
                }
            } else if (node instanceof TextField) {
                if (((TextField) node).getText().trim().isEmpty()) {
                    log.warn("Empty movie name field");
                    message.setText("Kérlek töltsd ki a mezőket");
                    message.setTextFill(Color.RED);
                    empty = true;
                    break;
                } else if (((TextField) node).getText().matches("-?\\d+(\\.\\d+)?")) {
                    log.warn("Tried to add number(" + ((TextField) node).getText() + ") instead of recipe name");
                    message.setText("Számokat nem adhatsz meg");
                    message.setTextFill(Color.RED);
                    empty = true;
                    break;
                } else {
                    message.setText("");
                    empty = false;
                }
            } else if (node instanceof VBox) {
                for (Node node1 : pane_main_grid.getChildren()) {
                    if (node1 instanceof TextField) {
                        if (((TextField) node1).getText().trim().isEmpty()) {
                            log.warn("Tried to add a number instead of ingredients");
                            message.setText("Kérlek töltsd ki a mezőket");
                            message.setTextFill(Color.RED);
                            empty = true;
                            break;
                        } else {
                            message.setText("");
                            empty = false;
                        }
                    }
                }
            }
        }


        if(!empty) {

            List<Actor> actors = new ArrayList<>();

            String movieDescription = movieDescriptionField.getText().toUpperCase();
            String movieName = movieNameField.getText().toUpperCase();
            String movieType = categoryComboBox.getValue().toString();

            movieDescriptionField.clear();
            movieNameField.clear();

            for (Node node : pane_main_grid.getChildren()) {
                if (node instanceof HBox) {
                    for (Node node1 : ((HBox) node).getChildren()) {
                        if (node1 instanceof TextField) {
                            actorName = ((TextField) node1).getText().toUpperCase();
                        }
                        else if (node1 instanceof ComboBox) {
                            typeName = ((ComboBox) node1).getValue().toString();
                        }
                    }
                    actors.add(new Actor(actorName, typeName));
                }
            }

            if (actors.isEmpty()) {
                message.setText("A színészek listáját nem töltötted ki!");
                message.setTextFill(Color.RED);
                log.warn("Tried to upload a movie without actors");
            } else {
                Movie movieIsEquals = new Movie(movieName, movieDescription, movieType, actors);
                for (int i = 0; i < movieServiceDAO.getAllMovie().size(); i++){
                    if (movieIsEquals.equals(movieServiceDAO.getAllMovie().get(i))){

                        isExist = true;
                        break;
                    } else {
                        isExist = false;
                    }
                }

                if(!isExist){
                    Movie movie = new Movie(movieName, movieDescription, movieType);
                    movieServiceDAO.createActorsAddToMovie(actors, movie);
                    movieServiceDAO.createMovie(movie);
                    message.setText("Sikeresen hozzáadtad a filmet!");
                    message.setTextFill(Color.GREEN);
                    pane_main_grid.getChildren().clear();
                    log.info("Added movie  successfully!");
                } else {
                    pane_main_grid.getChildren().clear();
                    message.setText("Ez a film már létezik!");
                    message.setTextFill(Color.RED);
                    log.info("The movie is already exist!");
                }


            }
        } else
            log.warn("The list of actors are empty");
    }

    @FXML
    public void AddTextField1() {

        log.info("Add a new TextField for actors");

        hBox = new HBox();

        newField = new TextField();
        HBox.setMargin(newField, new Insets(0, 10, 10, 20));
        HBox.setMargin(hBox, new Insets(0, 0, 10, 10));


        newField.setPrefWidth(150.0);
        newField.setPrefHeight(10.0);
        if (count <= 13) {
            pane_main_grid.getChildren().add(hBox);
            hBox.getChildren().add(newField);
            count++;
        } else {
            warningMessage.setText("Elérte a maximálisan megadható színészek számát!");
            log.warn("Tried to add new TextField more than 13");
        }
    }

    @FXML
    public void deleteTextField() {
            log.info("Deleted the last TextField");
            hBox.getChildren().remove(newField);
            count--;
    }
}
