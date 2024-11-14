package com.narriation;
import java.util.ArrayList;

/*
 * Words
 * 
 * @author LangBite Team
 */
public class Words {
    /*
     * Words implements a singleton pattern to manage the word objects
     * and allows acces to the list of words in the language
     */
    private static Words words = null;
    private static ArrayList<Word> wordList = new ArrayList<Word>();

    /*
     * Class Words initializes the word list by loading in the word JSON
     * information
     */
    // private Words() {
    //     wordList = DataLoader.loadWords();
    // }

    /*
     * Class getInstance gets the singleton instance of Words
     */
    public static Words getInstance() {
        if(words == null) {
            words = new Words();
        }
        return words;
    }

    /*
     * Class getWords retrieves the list of words
     */
    public ArrayList<Word> getWords() {
        return wordList;
    }

}


