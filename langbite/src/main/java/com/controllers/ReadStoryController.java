package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.util.Map;

import com.language.App;
import com.model.DataLoader;
import com.model.Story;
import com.model.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadStoryController {

    @FXML
    private Label storyTitleLabel;

    @FXML
    private TextFlow storyLineContainer;

    @FXML
    private Label storyLineLabel;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    private List<String> storyLines;
    private int currentLineIndex = 0;
    private Map<String, String> englishToFrenchMap;

    public void initialize() {
         buildTranslationMap();

        // Load stories
        List<Story> stories = DataLoader.loadStories();
        if (stories != null && !stories.isEmpty()) {
            Story story = stories.get(0); 
            storyTitleLabel.setText(story.getTitle());
            storyLines = List.of(story.getText().split("\\n"));
            updateStoryLine();
        } else {
            storyTitleLabel.setText("No Story Available");
            previousButton.setDisable(true);
            nextButton.setDisable(true);
        }
    }

    @FXML
    private void handlePrevious() {
        if (currentLineIndex > 0) {
            currentLineIndex--;
            updateStoryLine();
        }
    }

    @FXML
    private void handleNext() {
        if (currentLineIndex < storyLines.size() - 1) {
            currentLineIndex++;
            updateStoryLine();
        }
    }

    private void updateStoryLine() {
        storyLineContainer.getChildren().clear(); // Clear previous content
        storyLineContainer.setLineSpacing(5);
    
        String currentLine = storyLines.get(currentLineIndex);
        String[] words = currentLine.split(" ");
    
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            String frenchTranslation = englishToFrenchMap.get(cleanWord);
    
            Text wordText = new Text(word + " ");
            wordText.setStyle("-fx-font-size: 35px;");
            wordText.setTranslateX(5); 
    
            if (frenchTranslation != null) {
                wordText.setFill(javafx.scene.paint.Color.RED); // Initial color red
                wordText.setCursor(Cursor.HAND); 
                wordText.setOnMouseClicked(event -> {
                    if (wordText.getText().equals(word + " ")) {
                        wordText.setText(frenchTranslation + " ");
                    } else {
                        wordText.setText(word + " ");
                    }
                });
            } else {
                wordText.setFill(javafx.scene.paint.Color.BLACK); // All other words
            }
    
            storyLineContainer.getChildren().add(wordText); // Add the Text node to TextFlow
        }
    
        previousButton.setDisable(currentLineIndex == 0);
        nextButton.setDisable(currentLineIndex == storyLines.size() - 1);
    }
    
    
    
    

    private void buildTranslationMap() {
        englishToFrenchMap = new HashMap<>();
        List<Word> words = DataLoader.loadWords();
        if (words != null) {
            for (Word word : words) {
                englishToFrenchMap.put(word.getEnglish().toLowerCase(), word.getFrench());
            }
        } else {
            System.out.println("Failed to load words.");
        }
    }

    @FXML private void goHome() {
        try {
            App.setRoot("homepage");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load homepage.");
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

}
