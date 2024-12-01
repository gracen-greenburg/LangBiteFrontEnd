package com.controllers;

import java.io.IOException;
import com.language.App;
import com.narriation.Narriator;

import javafx.fxml.FXML;

public class HomePageController {
    
    @FXML private void switchToFlashcards() throws IOException {
        App.setRoot("flashcards");
    }

    @FXML private void switchToConversations() throws IOException {
        App.setRoot("conversations");
    }

    @FXML private void switchToStories() throws IOException {
        App.setRoot("stories");
    }

    @FXML private void switchToMiniGames() throws IOException {
        App.setRoot("minigameSelect");
    }
}
