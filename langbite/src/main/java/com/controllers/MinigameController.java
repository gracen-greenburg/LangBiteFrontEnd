package com.controllers;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.language.App;
import com.model.SessionManager;
import com.narriation.Narriator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class MinigameController {
    @FXML private void switchToFlashcards() throws IOException {
        App.setRoot("draganddrop");
    }

    @FXML private void switchToConversations() throws IOException {
        App.setRoot("matchinggame");
    }

    @FXML private void switchToStories() throws IOException {
        App.setRoot("stories");
    }
}
