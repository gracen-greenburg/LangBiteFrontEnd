package com.model.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // creates a list of flashcards with 15 words
        List<Flashcard> flashcardList = new ArrayList<>();
        flashcardList.add(new Flashcard("Apple", "Pomme"));
        flashcardList.add(new Flashcard("Dog", "Chien"));
        flashcardList.add(new Flashcard("House", "Maison"));
        flashcardList.add(new Flashcard("Car", "Voiture"));
        flashcardList.add(new Flashcard("Book", "Livre"));
        flashcardList.add(new Flashcard("Chair", "Chaise"));
        flashcardList.add(new Flashcard("Table", "Table"));
        flashcardList.add(new Flashcard("Water", "Eau"));
        flashcardList.add(new Flashcard("Bread", "Pain"));
        flashcardList.add(new Flashcard("Window", "Fenêtre"));
        flashcardList.add(new Flashcard("Tree", "Arbre"));
        flashcardList.add(new Flashcard("Sun", "Soleil"));
        flashcardList.add(new Flashcard("Moon", "Lune"));
        flashcardList.add(new Flashcard("Street", "Rue"));
        flashcardList.add(new Flashcard("Door", "Porte"));

        // creates flashcards to object with the flashcard list
        Flashcards flashcards = new Flashcards(flashcardList);

        // Creates a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        String input = "";

        // Show instructions
        System.out.println("Flashcard System:");
        System.out.println("Commands:");
        System.out.println(" - 'show': Show the current flashcard");
        System.out.println(" - 'reveal': Reveal the translation of the current flashcard");
        System.out.println(" - 'next': Go to the next flashcard");
        System.out.println(" - 'previous': Go to the previous flashcard");
        System.out.println(" - 'toggle': Toggle the front language (English/French)");
        System.out.println(" - 'shuffle': Shuffle the flashcards");
        System.out.println(" - 'exit': Exit the flashcard system");

        // main loop to handle user input
        while (!input.equals("exit")) {
            System.out.print("\nEnter a command: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "show":
                    flashcards.showFlashcard();
                    break;
                case "reveal":
                    flashcards.revealTranslation();
                    break;
                case "next":
                    flashcards.nextFlashcard();
                    break;
                case "previous":
                    flashcards.previousFlashcard();
                    break;
                case "toggle":
                    flashcards.toggleFrontLanguage();
                    break;
                case "shuffle":
                    flashcards.shuffleFlashcards();
                    break;
                case "exit":
                    System.out.println("Exiting the flashcard system.");
                    break;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }

        // closes the scanner when the user exits
        scanner.close();
    }
}