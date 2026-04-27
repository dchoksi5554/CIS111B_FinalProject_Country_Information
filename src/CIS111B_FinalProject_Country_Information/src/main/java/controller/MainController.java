package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Country;
import service.CountryService;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button anthemButton;
    @FXML private Label areaLabel;
    @FXML private Label capitalLabel;
    @FXML private ListView<Country> countryListView;
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

    private final CountryService countryService
            = new CountryService();
    private ObservableList<Country> allCountries
            = FXCollections.observableArrayList();
    private FilteredList<Country> filteredCountries;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set up filtered list connected to the ListView
        filteredCountries = new FilteredList<>(allCountries, c -> true);
        countryListView.setItems(filteredCountries);

        // When user clicks a country in the list
        countryListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        displayCountry(newVal);
                    }
                });

        // When user types in the search field
        searchField.textProperty()
                .addListener((obs, oldVal, newVal) -> {
                    String filter = newVal.toLowerCase().trim();
                    filteredCountries.setPredicate(country -> {
                        if (filter.isEmpty()) return true;
                        return country.getCommonName()
                                .toLowerCase().contains(filter);
                    });
                });

        // Load all countries when app starts
        loadCountries();
    }

    // Fetches countries from the API in the background
    private void loadCountries() {
        Task<List<Country>> task = new Task<>() {
            @Override
            protected List<Country> call() throws Exception {
                return countryService.fetchCountries("All");
            }
        };

        task.setOnSucceeded(e -> {
            allCountries.setAll(task.getValue());
        });

        task.setOnFailed(e -> {
            countryNameLabel.setText("Error loading countries.");
            System.out.println("Error: " + task.getException().getMessage());
            task.getException().printStackTrace();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // Fills in all the labels when a country is clicked
    private void displayCountry(Country country) {
        countryNameLabel.setText(country.getCommonName());
        officialNameLabel.setText(country.getOfficialName());
        capitalLabel.setText(country.getCapital());
        populationLabel.setText(country.getFormattedPopulation());
        areaLabel.setText(country.getFormattedArea());
        languageLabel.setText(country.getLanguages());
        currencyLabel.setText(country.getCurrencies());
        dialCodeLabel.setText(country.getDialCode());
        timezoneLabel.setText(country.getTimezones());

        // Load flag image in the background
        if (!country.getFlagUrl().isEmpty()) {
            Task<Image> imgTask = new Task<>() {
                @Override
                protected Image call() {
                    return new Image(
                            country.getFlagUrl(), 120, 78, true, true
                    );
                }
            };
            imgTask.setOnSucceeded(e ->
                    flagImage.setImage(imgTask.getValue())
            );
            new Thread(imgTask).start();
        }
    }

    // Anthem button opens YouTube
    @FXML
    void handleAnthemButton(ActionEvent event) {
        Country selected = countryListView
                .getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            Desktop.getDesktop().browse(
                    new URI(selected.getAnthemSearchUrl())
            );
        } catch (Exception e) {
            System.out.println("Could not open browser: "
                    + e.getMessage());
        }
    }

    // Maps button opens Google Maps
    @FXML
    void handleMapsButton(ActionEvent event) {
        Country selected = countryListView
                .getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            Desktop.getDesktop().browse(
                    new URI(selected.getGoogleMapsUrl())
            );
        } catch (Exception e) {
            System.out.println("Could not open browser: "
                    + e.getMessage());
        }
    }
}
