package com.model.Flashcard;
import java.util.Collections;
import java.util.List;
import com.narriation.Narriator;

/*
 * Flashcards
 * 
 * @author LangBite Team
 */
public class Flashcards {
    /*
     * Flashcards deals with the interactivity of flashcards,
     * such as the functions to display and reveal words, and to 
     * switch to the next one
     * 
     * @param flashcards List<Flashcard> a list of type Flashcard that holds all of the flashcards
     * @param currentIndex int keeps track of which flashcard the user is on
     * @param isEnglishFront boolean keeps track of whether the english word is on the front or not
     */
    private List<Flashcard> flashcards;
    private int currentIndex;
    private boolean isEnglishFront;

    /*
     * Class Flashcards instantiates all variables 
     */
    public Flashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.currentIndex = 0;
        this.isEnglishFront = true; 
    }

    /*
     * Class showFlashcard checks the index and selects which card 
     * the user is on. Then, determines which side the user is 
     * currently on (English or French). Depending on this, 
     * the console will show the English word or the French word
     */
    public void showFlashcard() {
        Flashcard currentFlashcard = flashcards.get(currentIndex);
        if (isEnglishFront) {
            System.out.println("English: " + currentFlashcard.getEnglishWord());
            Narriator.playSound(currentFlashcard.getEnglishWord());
        } else {
            System.out.println("French: " + currentFlashcard.getFrenchWord());
            Narriator.playSound(currentFlashcard.getFrenchWord());
        }
    }

    /*
     * Class revealTranslation checks index and selects which card 
     * the user is currently on. If the current side is english, 
     * it switches to the french translation. If it is currently 
     * on the french side, it switches to the english translation
     */
    public void revealTranslation() {
        Flashcard currentFlashcard = flashcards.get(currentIndex);
        if (isEnglishFront) {
            System.out.println("Translation (French): " + currentFlashcard.getFrenchWord());
            Narriator.playSound(currentFlashcard.getFrenchWord());
        } else {
            System.out.println("Translation (English): " + currentFlashcard.getEnglishWord());
            Narriator.playSound(currentFlashcard.getEnglishWord());
        }
    }

    /*
     * Class nextFlashcard adds to the currentIndex and progresses
     * to the next card, and calls the method showFlashcard() to
     * continue the game
     */
    public void nextFlashcard() {
        if (currentIndex < flashcards.size() - 1) {
            currentIndex++;
        } else {
            System.out.println("You are at the last flashcard.");
        }
        showFlashcard();
    }

    /*
     * Class previousFlashCard checks that as long as there were previous 
     * flashcards, the program subtracts from the currentIndex 
     * and goes to the previous card. If it is the first card, 
     * the program will inform the user
     */
    public void previousFlashcard() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            System.out.println("You are at the first flashcard.");
        }
        showFlashcard();
    }

    /*
     * Class toggleFrontLanguage checks to see: if English is 
     * in the front. If so, it flips to French. Otherwise, 
     * the French side will flip to the english side
     */
    public void toggleFrontLanguage() {
        isEnglishFront = !isEnglishFront;
        showFlashcard();
    }

    /*
     * Class shuffleFlashcards ttilizes the Collections.shuffle tool, 
     * and shuffles the flashcards. Then, the index is set to 0 to 
     * start from the beginning, and calls the showFlashcard()
     * to continue
     */
    public void shuffleFlashcards() {
        Collections.shuffle(flashcards);
        currentIndex = 0; 
        System.out.println("Flashcards shuffled.");
        showFlashcard();
    }

    public Flashcard getCurrentFlashcard() {
        return flashcards.get(currentIndex);
    }
    
    public List<Flashcard> getFlashcardsList() {
        return flashcards;
    }
    
    public boolean isEnglishFront() {
        return isEnglishFront;
    }
}