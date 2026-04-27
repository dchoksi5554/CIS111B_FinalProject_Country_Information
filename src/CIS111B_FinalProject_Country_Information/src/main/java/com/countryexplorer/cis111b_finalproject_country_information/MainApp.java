package com.countryexplorer.cis111b_finalproject_country_information;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/countryexplorer/cis111b_finalproject_country_information/main.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);

        scene.getStylesheets().add(
                getClass().getResource("/com/countryexplorer/cis111b_finalproject_country_information/styles.css")
                        .toExternalForm()
        );

        primaryStage.setTitle("Country Explorer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void main(String[] args) {
        launch(args);
    }
}