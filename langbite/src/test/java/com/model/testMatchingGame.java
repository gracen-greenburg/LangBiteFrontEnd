package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.MatchingGame.MatchingGame;

import org.junit.Test;

public class testMatchingGame {
    
// Tests to see if the answers are correctly matched
@Test
public void testConstructor() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    assertEquals(2, game.getCorrectMatchesCount()); 
    assertTrue(game.getUserMatches().isEmpty()); // at the start the user matches list is empty
    assertTrue(game.getCorrectUserMatches().isEmpty()); // so is the correct user matches list
}

// Tests to see if shuffle words really randomizes the order of the words
@Test
public void testShuffleWords() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);
    
    List<String> englishWords = new ArrayList<>(game.getShuffledEnglishWords());
    List<String> frenchWords = new ArrayList<>(game.getShuffledFrenchWords());
    game.shuffleWords();
    
    assertNotEquals(englishWords, game.getShuffledEnglishWords()); // should not be the same becasue shuffled
    assertNotEquals(frenchWords, game.getShuffledFrenchWords()); // should not be the same because shuffled
}

// Tests to see if the user making a correct match works
@Test
public void testMakeCorrectMatch() {
    Map<String, String> correctMatches = Map.of("apple", "pomme");
    MatchingGame game = new MatchingGame(correctMatches);
    
    game.makeMatch("apple", "pomme");
    assertTrue(game.getCorrectUserMatches().contains("apple")); // should be true because it is correct
}

// Tests to see if the user making an incorrect match works
@Test
public void testMakeIncorrectMatch() {
    Map<String, String> correctMatches = Map.of("apple", "pomme");
    MatchingGame game = new MatchingGame(correctMatches);
    
    game.makeMatch("apple", "banane");
    assertFalse(game.getCorrectUserMatches().contains("apple")); // should be false because incorrect
}

// Tests to see if the answer will be accepted if uppercase or in mixed case
@Test
public void testMakeMatchLetterCasing() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("Apple", "POMME");
    game.makeMatch("cAr", "vOiTuRe");
    assertTrue(game.getCorrectUserMatches().contains("apple")); // Should be true
    assertTrue(game.getCorrectUserMatches().contains("car")); // Should be true
}

// Tests to see what happens when the user tries to make the same match more than once
@Test
public void testDuplicateMatchAttempt() {
    Map<String, String> correctMatches = Map.of("apple", "pomme");
    MatchingGame game = new MatchingGame(correctMatches);
    
    game.makeMatch("apple", "pomme");
    game.makeMatch("apple", "pomme"); 
    assertEquals(1, game.getCorrectUserMatches().size()); // Only counts as correct for one
}

// Tests to see when user submits an item not in list currently
@Test
public void testNonExistentWordMatch() {
    Map<String, String> correctMatches = Map.of("apple", "pomme");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("pear", "pomme"); // pear is not in the list

    assertFalse(game.getUserMatches().containsKey("pear")); // No match should be added
}

// Tests if right output when all submitted answers are correct
@Test
public void testSubmitAnswerIfAllCorrect() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "pomme"); // Correct
    game.makeMatch("car", "voiture"); // Incorrect

    assertTrue(game.submitAnswer());
}

// Tests if right output when not all submitted answers are correct
@Test
public void testSubmitAnswerIfSomeCorrect() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "pomme"); // Correct
    game.makeMatch("car", "bicycle");  // Incorrect

    assertFalse(game.submitAnswer()); // Should be false because not every answer was correct
}

// // Tests if right output when no submitted answers are correct
@Test
public void testSubmitAnswerIfNoneCorrect() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "banana"); // Incorrect match
    game.makeMatch("car", "bicycle");  // Incorrect match

    assertFalse(game.submitAnswer()); // Should be false because not every answer was correct
}

// Tests if right output when correct matches are counted
@Test
public void testGetCorrectMatchesCount() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "pomme"); // COrrect
    game.makeMatch("car", "bicycle"); // Incorrect

    assertEquals(1, game.getCorrectMatchesCount()); // Should be equal to 1 because one answer correct
}

// Tests to see if the game resets properly
@Test
public void testResetGame() {
    Map<String, String> correctMatches = Map.of("apple", "pomme");
    MatchingGame game = new MatchingGame(correctMatches);
    
    game.makeMatch("apple", "pomme");
    game.resetGame(); 
    
    assertTrue(game.getUserMatches().isEmpty()); // user matches should be empty
    assertTrue(game.getCorrectUserMatches().isEmpty()); // correct user matches should be empty
    assertNotEquals(correctMatches.keySet(), game.getShuffledEnglishWords()); // Confirm reshuffled
}

// Tests to see if game ends when user gets all answers correct
@Test
public void testIsGameCompleteWhenAllCorrect() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "pomme");
    game.makeMatch("car", "voiture");

    assertTrue(game.isGameComplete()); // Game should end when user gets all correct
}

// Tests to see if game ends when user doesn't get all answers correct
@Test
public void testIsGameCompleteWhenSomeNotCorrect() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "pomme"); 

    assertFalse(game.isGameComplete());  // Game should not end because the user did not get everything correct
}

// Tests whether game registered as complete when the user hasn't answred all questions
@Test
public void testPartialGameCompletion() {
    Map<String, String> correctMatches = Map.of("apple", "pomme", "car", "voiture", "house", "maison");
    MatchingGame game = new MatchingGame(correctMatches);

    game.makeMatch("apple", "pomme");
    game.makeMatch("car", "voiture");

    assertFalse(game.isGameComplete()); // Only partial matches, so the game is not complete
}

}
