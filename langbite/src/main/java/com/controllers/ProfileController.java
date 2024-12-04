package com.controllers;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import com.language.App;
import com.model.DataConstants;
import com.model.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class ProfileController {

    // Path to User.json file
    private static final String USER_JSON_PATH = "src/main/resources/com/language/User.json";

    // Method to navigate to the Home Page
    @FXML
    private void goHomePage() {
        try {
            App.setRoot("homepage"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
