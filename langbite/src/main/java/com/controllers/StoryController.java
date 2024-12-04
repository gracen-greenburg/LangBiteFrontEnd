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

import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class StoryController {

    @FXML private TextField searchField;
    @FXML private GridPane bookGrid;
    @FXML private Button previousPageButton;
    @FXML private Button nextPageButton;

    private List<JSONObject> stories;  // Placeholder for loaded story JSON objects
    private int currentPage = 0;
    private final int STORIES_PER_PAGE = 4;  // Number of stories per page

    @FXML
    private void initialize() {
        loadStoriesFromJson("stories.json");
        loadStoriesOnPage(currentPage);
    }

    private void loadStoriesFromJson(String filePath) {
        JSONParser parser = new JSONParser();
        stories = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray storiesArray = (JSONArray) parser.parse(reader);
            for (Object obj : storiesArray) {
                JSONObject storyJson = (JSONObject) obj;
                stories.add(storyJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStoriesOnPage(int pageIndex) {
        bookGrid.getChildren().clear();  // Clear the previous page

        int startIndex = pageIndex * STORIES_PER_PAGE;
        int endIndex = Math.min(startIndex + STORIES_PER_PAGE, stories.size());

        int column = 0;
        int row = 0;

        for (int i = startIndex; i < endIndex; i++) {
            JSONObject story = stories.get(i);
            VBox storyItem = createStoryItem(story);
            bookGrid.add(storyItem, column, row);

            column++;
            if (column == 2) { // Adjust based on your desired number of columns
                column = 0;
                row++;
            }
        }
    }

    private VBox createStoryItem(JSONObject story) {
        VBox storyBox = new VBox(10);
        String storyTitle = (String) story.get("storyTitle");
        String storyCoverPath = "images/" + (String) story.get("storyCover");

        ImageView storyCover = new ImageView(new Image(storyCoverPath));
        storyCover.setFitWidth(100);
        storyCover.setFitHeight(150);
        
        Label titleLabel = new Label(storyTitle);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        storyBox.getChildren().addAll(storyCover, titleLabel);
        storyBox.setOnMouseClicked(event -> handleStorySelection(story));

        return storyBox;
    }

    private void handleStorySelection(JSONObject story) {
        String storyTitle = (String) story.get("storyTitle");
        String storyText = (String) story.get("storyText");

        // Display story details or load the reading view
        System.out.println("Selected story: " + storyTitle);
        System.out.println("Story text: " + storyText);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();

        bookGrid.getChildren().clear();  // Clear previous search results

        int column = 0;
        int row = 0;

        for (JSONObject story : stories) {
            String storyTitle = (String) story.get("storyTitle");
            if (storyTitle.toLowerCase().contains(searchText)) {
                VBox storyItem = createStoryItem(story);
                bookGrid.add(storyItem, column, row);
                
                column++;
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    @FXML
    private void handlePreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadStoriesOnPage(currentPage);
        }
    }

    @FXML
    private void handleNextPage() {
        if ((currentPage + 1) * STORIES_PER_PAGE < stories.size()) {
            currentPage++;
            loadStoriesOnPage(currentPage);
        }
    }
}
