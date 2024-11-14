package com.model.Flashcard;

/*
 * Flashcard
 * 
 * @author LangBite Team
 */
public class Flashcard {
    /*
     * Flashcard handles the technical aspects dealing with french
     * and english words in relation to the Flashcards class
     * 
     * @param englishWord String English word 
     * @param frenchWord String French word
     */
    private String englishWord;
    private String frenchWord;

    /*
     * Class Flashcard instantiates englishWord and frenchWord 
     * variables
     */
    public Flashcard(String englishWord, String frenchWord) {
        this.englishWord = englishWord;
        this.frenchWord = frenchWord;
    }

    /*
     * Class getEnglishWord returns the englishWord String
     */
    public String getEnglishWord() {
        return englishWord;
    }

    /*
     * Class getFrenchWord returns frenchWord String
     */
    public String getFrenchWord() {
        return frenchWord;
    }

    /*
     * Class displayFlashcard takes in a boolean isEnglishFront, 
     * which is asking if the english word is on the front of the 
     * card. This method returns the string englishWord or frenchWord
     *  depending on which side it is on
     */
    public String displayFlashcard(boolean isEnglishFront) {
        return isEnglishFront ? englishWord : frenchWord;
    }
}