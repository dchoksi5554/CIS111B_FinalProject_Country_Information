# CIS111B Final Project Country Information

This app gets the information of a country from an api and returns it in a graphical user interface. The left panel allows the user to scroll or search for the country they would like information for. The right panel shows the flag of the country as well as the capital, population, area, main language(s), currency, dial code, and time zone. Also, there are buttons that, when pressed, allow the user to listen to the national anthem and see the country on a map. 

# Important Files:
* MainApp.java - Contains the code to load up the FXML file as well as create and title the scene.
* MainController.java - Sets the information, like the link to the national anthem and the map of the country, to the buttons. Also, sets the other information to the labels set in the FXML file.
* Country.java - Uses the class hierarchy of the JSON from the API to create a plain-old-java-object. This is so the information can be used in the rest of the program.
* CountryService.java - Calls the API, then parses it using GSON. This file inserts the wanted fields to call the right URL for searching for the countries and to get the countries' information.
* main.fxml - This file contains the different features that will be on the user interface. It creates the id to that the controller can pass information to be displayed by the interface.
* styles.css - This file sets how the interface will look. In this file, the font sizes/weight, margins, border colors/weight, and background colors are set.

# Notes
* This project was created in the IntelliJ IDE.
* To view the file, the user must have the newest version of JavaFX downloaded and added to the VM options in the run configurations.
* Must reload all Maven projects before running the app to download the necessary files for the program to work.
