package com.controllers;

import java.io.IOException;

import com.language.App;

import javafx.fxml.FXML;

public class ProfileController {

    // Method to navigate to the Home Page
    @FXML
    private void goHomePage() {
        try {
            App.setRoot("homepage"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
