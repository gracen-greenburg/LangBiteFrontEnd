package com.controllers;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.language.App;
import com.model.LanguageList;


import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class LanguageSelectionController  {

    private JSONObject currentUser;
    private LanguageList languageList;

    
    @FXML private Button addLanguageButton;


    // Sets Current user and intializes the LanguageList
     public void setCurrentUser(JSONObject currentUser) {
        this.currentUser = currentUser;
        this.languageList = new LanguageList(currentUser);
    }

    
    /**
     * Handles the Add Language button click event.
     * Adds the language to the user's list and navigates to the home screen.
     */
    @FXML private void handleAddLanguage() throws IOException {
        addLanguageToUser();
        switchToHomePage();
    }

    
    // Adds the French language to the user's language list.
     private void addLanguageToUser() {
        if (languageList != null) {
            languageList.addLanguage();
        }
    }

    // Switches to home page
    @FXML private void switchToHomePage() throws IOException {
        App.setRoot("homepage");
    }

    

}