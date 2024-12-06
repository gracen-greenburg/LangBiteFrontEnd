package com.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import com.language.App;

public class TakeABreakController {

    @FXML
    private ImageView gatorImageView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button homeButton;

    @FXML
    private Label scoreLabel;

    private int score = 0;
    private Random random = new Random();
    private Timeline coinTimeline;
    private double spawnInterval = 0.80; 

    @FXML
    public void initialize() {
        URL gatorImageUrl = getClass().getResource("/com/language/images/runningGator.png");
        if (gatorImageUrl != null) {
            Image gatorImage = new Image(gatorImageUrl.toExternalForm());
            gatorImageView.setImage(gatorImage);
        } else {
            System.err.println("Image not found at /com/language/images/runningGator.png");
        }
        gatorImageView.setVisible(true);
        gatorImageView.setPreserveRatio(true);
        gatorImageView.setSmooth(true);
        gatorImageView.setFitWidth(100);
        gatorImageView.setFitHeight(100);
        addBackgroundText();
        startSpawningCoins();
        anchorPane.setOnMouseMoved(event -> handleMouseMovement(event.getX(), event.getY()));
    }

     @FXML
    private void handleHomeButtonClick(ActionEvent event) {
        try {
            App.setRoot("homepage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBackgroundText() {
        Label backgroundText = new Label("TAKE A BRAIN BREAK");
        backgroundText.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: green;");
    
        // Add the label to the AnchorPane first
        anchorPane.getChildren().add(0, backgroundText);
    
        // Center the label in the AnchorPane
        double paneWidth = anchorPane.getWidth();
        double paneHeight = anchorPane.getHeight();
        
        backgroundText.setLayoutX((paneWidth - backgroundText.getWidth()) / 2);
        backgroundText.setLayoutY(50);  // Keep the vertical position fixed at 50 px from the top
    
        // Add listeners to update position on resizing
        anchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundText.setLayoutX((newVal.doubleValue() - backgroundText.getWidth()) / 2);
        });
    
        backgroundText.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundText.setLayoutX((anchorPane.getWidth() - newVal.doubleValue()) / 2);
        });
    }

    private void handleMouseMovement(double mouseX, double mouseY) {
        // Gator with mouse movement
        double targetX = mouseX - gatorImageView.getFitWidth() / 2;
        double targetY = mouseY - gatorImageView.getFitHeight() / 2;

        gatorImageView.setLayoutX(targetX);
        gatorImageView.setLayoutY(targetY);

        checkCoinCollision();
    }

    private void startSpawningCoins() {
        // spawn coins
        coinTimeline = new Timeline(new KeyFrame(Duration.seconds(spawnInterval), event -> {
            spawnCoin();
            if (spawnInterval > 0.5) { 
                spawnInterval -= 0.2;  
            }
            
            coinTimeline.stop();
            coinTimeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(spawnInterval), e -> spawnCoin()));
            coinTimeline.play();
        }));
        coinTimeline.setCycleCount(Timeline.INDEFINITE); // happens until teh user clicks to end their break
        coinTimeline.play();
    }

    private void spawnCoin() {
        ImageView coin = new ImageView();
        URL coinImageUrl = getClass().getResource("/com/language/images/coin.png");
        if (coinImageUrl != null) {
            Image coinImage = new Image(coinImageUrl.toExternalForm());
            coin.setImage(coinImage);
        } else {
            System.err.println("Coin image not found at /com/language/images/coin.png");
            return;
        }

        coin.setFitWidth(50);
        coin.setFitHeight(50);

        //random coin position
        double x = random.nextDouble() * (anchorPane.getWidth() - coin.getFitWidth());
        double y = random.nextDouble() * (anchorPane.getHeight() - coin.getFitHeight());
        coin.setLayoutX(x);
        coin.setLayoutY(y);

        anchorPane.getChildren().add(coin);
    }


    private void checkCoinCollision() {
        for (int i = 0; i < anchorPane.getChildren().size(); i++) {
            if (anchorPane.getChildren().get(i) instanceof ImageView) {
                ImageView node = (ImageView) anchorPane.getChildren().get(i);
                if (node != gatorImageView) {
                    if (isColliding(gatorImageView, node)) {
                        anchorPane.getChildren().remove(node);
                        score++;
                        updateScoreLabel();
                        break;
                    }
                }
            }
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Coins: " + score);
    }

    private boolean isColliding(ImageView gator, ImageView coin) {
        Rectangle gatorBounds = new Rectangle(gator.getLayoutX(), gator.getLayoutY(), gator.getFitWidth(), gator.getFitHeight());
        Rectangle coinBounds = new Rectangle(coin.getLayoutX(), coin.getLayoutY(), coin.getFitWidth(), coin.getFitHeight());

        return gatorBounds.getBoundsInParent().intersects(coinBounds.getBoundsInParent());
    }
}
