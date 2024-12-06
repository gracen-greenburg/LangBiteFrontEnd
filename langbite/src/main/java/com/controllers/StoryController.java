package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.language.App;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class StoryController {

    @FXML private Button goToStory;
    
    @FXML
    private void goToStory() throws IOException {
         App.setRoot("readstory");
    }
}
