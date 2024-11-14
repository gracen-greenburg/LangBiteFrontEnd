package com.model.DragandDrop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // List of French words and their correct English categories
        List<String> draggableItems = Arrays.asList("Pomme", "Epinard", "Raisin", "Chou", "Pasteque", "Laitue", "Ananas");
        List<String> dropTargets = Arrays.asList("Fruit (Le Fruit)", "Vegetable (Le Legume)");

        // Map of correct French words to English categories
        Map<String, String> correctMatches = new HashMap<>();
        correctMatches.put("Pomme", "Fruit");
        correctMatches.put("Epinard", "Vegetable");
        correctMatches.put("Raisin", "Fruit");
        correctMatches.put("Chou", "Vegetable");
        correctMatches.put("Pasteque", "Fruit");
        correctMatches.put("Laitue", "Vegetable");
        correctMatches.put("Ananas", "Fruit");

        // Create a DragAndDrop instance with the draggable items, target categories, and correct matches
        DragAndDrop dragAndDrop = new DragAndDrop(draggableItems, dropTargets, correctMatches);

        // Set up scanner for user input
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("Welcome to the Drag and Drop Game!");

        // Main game loop
        while (!input.equalsIgnoreCase("exit")) {
            // Display draggable items and targets
            dragAndDrop.displayDraggableItems();
            dragAndDrop.displayTargets();

            // Ask user for input
            System.out.print("\nEnter the French word to drag (or type 'submit' to check your answers, 'reset' to restart, 'next' to proceed, or 'exit' to quit): ");
            input = scanner.nextLine().toLowerCase(); // convert to lowercase for case-insensitivity

            if (input.equalsIgnoreCase("submit")) {
                // Display final score
                if (dragAndDrop.submitAnswer()) {
                    System.out.println("Congratulations! You matched all the items correctly.");
                }
                break;
            } else if (input.equalsIgnoreCase("reset")) {
                dragAndDrop.resetGame();
            } else if (input.equalsIgnoreCase("next")) {
                System.out.println("Proceeding to the next word...");
            } else if (!input.equalsIgnoreCase("exit")) {
                System.out.print("Enter the target category (Fruit or Vegetable): ");
                String category = scanner.nextLine().toLowerCase(); // convert to lowercase for case-insensitivity
                dragAndDrop.dragAndDropItem(input, category);
            }
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }
}