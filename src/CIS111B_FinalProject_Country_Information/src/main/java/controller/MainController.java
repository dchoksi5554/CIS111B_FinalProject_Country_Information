package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML private Button anthemButton;
    @FXML private Label areaLabel;
    @FXML private Label capitalLabel;
    @FXML private ListView<?> countryListView;
    @FXML private Label countryNameLabel;
    @FXML private Label currencyLabel;
    @FXML private Label dialCodeLabel;
    @FXML private ImageView flagImage;
    @FXML private Label languageLabel;
    @FXML private Button mapsButton;
    @FXML private Label officialNameLabel;
    @FXML private Label populationLabel;
    @FXML private TextField searchField;
    @FXML private Label timezoneLabel;

    @FXML
    void handleAnthemButton(ActionEvent event) {
    }

    @FXML
    void handleMapsButton(ActionEvent event) {
    }
}
