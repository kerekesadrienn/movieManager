package controller;

import dao.MovieDAO;
import dao.impl.MovieImpl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.*;
import org.slf4j.LoggerFactory;
import service.MovieService;
import service.MovieServiceImpl;
import utility.DBManager;

import java.util.ArrayList;
import java.util.List;

public class SearchController {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(SearchController.class);

    @FXML
    public HBox hboxPane;

    @FXML
    public VBox vboxPane0;

    @FXML
    public VBox vboxPane1;

    @FXML
    public VBox vboxPane2;

    @FXML
    public Text warningMessage;

    @FXML
    public SplitPane splitPane;

    @FXML
    public CheckBox checkBox1;

    @FXML
    public CheckBox checkBox2;

    @FXML
    public CheckBox checkBox3;

    @FXML
    public CheckBox checkBox4;

    @FXML
    public CheckBox checkBox5;

    @FXML
    public CheckBox checkBox6;

    @FXML
    public CheckBox checkBox7;

    @FXML
    public CheckBox checkBox8;

    @FXML
    public TextArea getMovieDescription;

    @FXML
    public TextArea getActors;

    @FXML
    private ListView<String> movieView;


    private List<String> movieItems = new ArrayList<>();
    private List<Movie> resultMovies;
    private int count = 0;

    private MovieService movieService;

    public SearchController() {
        MovieDAO dao = new MovieImpl(DBManager.getInstance());
        movieService = new MovieServiceImpl(dao);
    }

    public void initialize(){
        movieView.setVisible(true);
        splitPane.setVisible(true);

        checkBox1.setText(Category.VÍGJÁTÉK.name());
        checkBox2.setText(Category.DRÁMA.name());
        checkBox3.setText(Category.THRILLER.name());
        checkBox4.setText(Category.HORROR.name());
        checkBox5.setText(Category.AKCIÓ.name());
        checkBox6.setText(Category.SCIFI.name());
        checkBox7.setText(Category.DOKUMENTUMFILM.name());
        checkBox8.setText(Category.ROMANTIKUS.name());

    }

    @FXML
    public void handleClickListView() {

        if (!resultMovies.isEmpty()) {
            movieView.setVisible(true);
            String element = movieView.getSelectionModel().getSelectedItem();

            for (Movie aResultMovie : resultMovies) {
                if (element.equals(aResultMovie.getName())) {
                    getMovieDescription.setText(aResultMovie.getDescription());
                    getActors.setText("");
                    for (int j = 0; j < aResultMovie.getActors().size(); j++) {
                        getActors.appendText(aResultMovie.getActors().get(j).getName());
                        getActors.appendText("\n");
                    }
                }
            }
            log.info("Choose the " + element + " movie");
        }
    }

    @FXML
    public void addSearchField() {

        log.info("Add a new TextField for search ...");

        TextField newField = new TextField();
        VBox.setMargin(newField, new Insets(0,0,5,0));
        newField.setPrefWidth(160.0);
        newField.setPrefHeight(25.0);
        if (0 <= count && count < 5) {
            vboxPane0.getChildren().add(newField);
            count++;
        }
        else if(5 <= count && count < 10){
            vboxPane1.getChildren().add(newField);
            count++;
        }
        else if(10 <= count && count < 15){
            vboxPane2.getChildren().add(newField);
            count++;
        }
        else{
            warningMessage.setText("Maximum 15 színészt adhatsz hozzá !");
            log.debug("Tried to add more TextField than 15");
        }
    }

    private List<String> actorList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private List<String> containList = new ArrayList<>();
    @FXML
    public void searchItems() {
        log.info("Searched for the movies");

        getMovieDescription.setText("");
        getActors.setText("");

        warningMessage.setText("");
        movieItems.clear();

        for (Node node : hboxPane.getChildren()) {
            if (node instanceof VBox) {
                for (Node node1 : ((VBox) node).getChildren()) {
                    if (node1 instanceof TextField) {
                        String searchItem = ((TextField) node1).getText().trim().toUpperCase();
                        actorList.add(searchItem);
                    }
                }
            }
        }
        resultMovies = movieService.searchMovie(actorList);

        if(resultMovies.isEmpty()){
            warningMessage.setText("Nincs a keresésnek megfelelő film az adatbázisban");
            log.info("The search was unsuccessfully");
        } else {
            movieView.setVisible(true);
            splitPane.setVisible(true);
            for (Movie aResultMovie : resultMovies) {
                movieItems.add(aResultMovie.getName());
            }
            log.info("The search was successfully");
        }
        movieView.getItems().setAll(movieItems);
        movieView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void filter() {

        getMovieDescription.setText("");
        getActors.setText("");
        warningMessage.setText("");
        movieItems.clear();

        typeList = new ArrayList<>();

        if(checkBox1.isSelected())
            typeList.add(checkBox1.getText().toUpperCase());
        if(checkBox2.isSelected())
            typeList.add(checkBox2.getText().toUpperCase());
        if(checkBox3.isSelected())
            typeList.add(checkBox3.getText().toUpperCase());
        if(checkBox4.isSelected())
            typeList.add(checkBox4.getText().toUpperCase());
        if(checkBox5.isSelected())
            typeList.add(checkBox5.getText().toUpperCase());
        if(checkBox6.isSelected())
            typeList.add(checkBox6.getText().toUpperCase());
        if(checkBox7.isSelected())
            typeList.add(checkBox7.getText().toUpperCase());
        if(checkBox8.isSelected())
            typeList.add(checkBox8.getText().toUpperCase());

        log.info("Filtered the movies: " + typeList.toString());

        List<Movie> filteredMovie = movieService.searchFilteredMovie(typeList, actorList);

        System.out.println("filteredM: " + filteredMovie);
        if(filteredMovie.size() == 0){
            filteredMovie = movieService.getMoviesByCategories(typeList);
        }

        for (Movie aFilteredMovie : filteredMovie) {
            movieItems.add(aFilteredMovie.getName());
        }

        movieView.getItems().setAll(movieItems);
        movieView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
        checkBox4.setSelected(false);
        checkBox5.setSelected(false);
        checkBox6.setSelected(false);
        checkBox7.setSelected(false);
        checkBox8.setSelected(false);

    }

    @FXML
    public void contains() {

        getMovieDescription.setText("");
        getActors.setText("");
        warningMessage.setText("");
        movieItems.clear();

        containList = new ArrayList<>();


        log.info("Filtered movies: " + containList.toString());

        List<Movie> filteredMovie = movieService.searchContainedMovie(containList, typeList, actorList);

        for (int i = 0; i < filteredMovie.size(); i++) {
            Movie aFilteredMovie = filteredMovie.get(i);
            movieItems.add(aFilteredMovie.getName());
        }

        movieView.getItems().setAll(movieItems);
        movieView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }
}
