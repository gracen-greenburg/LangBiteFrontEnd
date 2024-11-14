package com.model.MatchingGame;

import java.util.*;

public class Main {
    public static void main(String[] args) {
    	// English to French word matches
        Map<String, String> correctMatches = new HashMap<>();
        correctMatches.put("Apple", "Pomme");
        correctMatches.put("House", "Maison");
        correctMatches.put("Dog", "Chien");
        correctMatches.put("Cat", "Chat");
        correctMatches.put("Water", "Eau");
        correctMatches.put("Tree", "Arbre");
        correctMatches.put("Car", "Voiture");

        // Creates the MatchingGame instance
        MatchingGame matchingGame = new MatchingGame(correctMatches);

        // Creates a Scanner instance for user input
        Scanner scanner = new Scanner(System.in);
        String input = "";

        // displays items to match
        matchingGame.displayItemsToMatch();

        // main game loop
        while (!input.equals("exit")) {
            System.out.print("\nEnter an English word to match (or type 'submit' to check progress, 'exit' to quit): ");
            input = scanner.nextLine();

            if (input.equals("exit")) {
                matchingGame.displayScore();  // show the score before exiting
                System.out.println("Thank you for playing!");
                break;
            } else if (input.equals("submit")) {
                matchingGame.displayScore();
            } else {
                System.out.print("Enter the matching French word for " + input + ": ");
                String frenchWord = scanner.nextLine();
                matchingGame.makeMatch(input, frenchWord);
            }

            // Ask if they want to restart when the game is complete
            if (matchingGame.isGameComplete()) {
                System.out.println("Congratulations! You've completed the matching game! Would you like to play again? (yes/no)");
                String playAgain = scanner.nextLine();
                if (playAgain.equalsIgnoreCase("yes")) {
                    matchingGame.resetGame();
                    matchingGame.displayItemsToMatch();
                } else if (playAgain.equalsIgnoreCase("no")) {
                    System.out.println("Thank you for playing! Goodbye!");
                    break;
                }
            }
        }

        scanner.close();
    }
}