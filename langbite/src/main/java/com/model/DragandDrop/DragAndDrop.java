package com.model.DragandDrop;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Class DragAndDrop
 * 
 * @author LangBite Team
 */

public class DragAndDrop {
    private Map<String, String> correctMatches; 
    private Map<String, String> userMatches;    
    private List<String> draggableItems;        
    private List<String> dropTargets;            

    // Valid categories in lowercase for validation
    private static final Set<String> VALID_CATEGORIES = new HashSet<>(Arrays.asList("fruit", "vegetable"));

    /*
     * Class DragAndDrop instantiates all of the aforementioned variables
     */
    public DragAndDrop(List<String> draggableItems, List<String> dropTargets, Map<String, String> correctMatches) {
        this.draggableItems = draggableItems;
        this.dropTargets = dropTargets;
        this.correctMatches = new HashMap<>();
        this.userMatches = new HashMap<>();

        for (Map.Entry<String, String> entry : correctMatches.entrySet()) {
            // Store all words and categories in lowercase for case-insensitive matching
            this.correctMatches.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
        }
    }

    /*
     * Class displayDraggableItems cycles through every french word
     *  on the list draggable items and prints them to console
     */
    public void displayDraggableItems() {
        System.out.println("\nDraggable French words:");
        for (String item : draggableItems) {
            System.out.println(" - " + capitalizeFirstLetter(item));
        }
    }

    /*
     * Class displayTargets cycles through every english target
     * word and prints to console
     */
    public void displayTargets() {
        System.out.println("\nTarget categories:");
        for (int i = 0; i < dropTargets.size(); i++) {
            System.out.println((i + 1) + ". " + dropTargets.get(i));
        }
    }

    /*
     * Class dragAndDropItem takes in user input of the draggable 
     * word and the target and adds that to the Map of userMatches
     */
    public void dragAndDropItem(String frenchWord, String category) {
        String lowercaseCategory = category.toLowerCase();
        if (!VALID_CATEGORIES.contains(lowercaseCategory)) {
            System.out.println("Invalid category. Please enter 'Fruit' or 'Vegetable'.");
            return;
        }

        userMatches.put(frenchWord.toLowerCase(), lowercaseCategory);
        System.out.println("You placed " + capitalizeFirstLetter(frenchWord) + " into the " + capitalizeFirstLetter(lowercaseCategory) + " category.");
    }

    /*
     * Class submitAnswer goes through the correctMatches and 
     * userMatches and compares the answers. If the user was correct, 
     * then that is an additional point to their score
     */
    public boolean submitAnswer() {
        System.out.println("\nChecking your matches...");
        int correctCount = 0;

        for (Map.Entry<String, String> entry : userMatches.entrySet()) {
            String correctCategory = correctMatches.get(entry.getKey().toLowerCase());
            if (correctCategory != null && correctCategory.equalsIgnoreCase(entry.getValue())) {
                correctCount++;
            }
        }

        int totalItems = correctMatches.size();
        System.out.println("You got " + correctCount + " out of " + totalItems + " matches correct.");
        return correctCount == totalItems;
    }

    /*
     * Class resetGame clears the userMatches map, and has the 
     * potential to start the game over
     */
    public void resetGame() {
        userMatches.clear();
        System.out.println("Game reset. Try again!");
    }

    // Getter for draggable items
    public List<String> getDraggableItems() {
        return draggableItems;
    }

    public Map<String, String> getUserMatches() {
        return new HashMap<>(userMatches); // Returns user Matches
    }

    public Map<String, String> getCorrectMatches() {
        return new HashMap<>(correctMatches); // Returns correct matches
    }
    
    public List<String> getDropTargets() {
        return new ArrayList<>(dropTargets); // Returns drop targets
    }

    private String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}