package com.controllers;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.language.App;
import com.model.SessionManager;
import com.narriation.Narriator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class MinigameController {
    @FXML private void switchToDragAndDrop() throws IOException {
        App.setRoot("draganddrop");
    }

    @FXML private void switchToMatching() throws IOException {
        App.setRoot("matchinggame");
    }

    @FXML private void switchToBreak() throws IOException {
        App.setRoot("takeabreak");
    }
}
