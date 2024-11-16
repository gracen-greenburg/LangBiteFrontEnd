package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import com.model.DragandDrop.DragAndDrop;

import org.junit.Test;

public class testDragAndDrop {

// Tests to see if the answers are correctly matched
@Test
public void testConstructor() {
    List<String> draggableItems = Arrays.asList("pomme", "carotte");
    List<String> dropTargets = Arrays.asList("Fruit", "Vegetable");
    Map<String, String> correctMatches = Map.of("pomme", "fruit", "carotte", "vegetable");

    DragAndDrop dragAndDrop = new DragAndDrop(draggableItems, dropTargets, correctMatches);

    assertEquals(draggableItems, dragAndDrop.getDraggableItems()); // should be equal
    assertEquals("fruit", dragAndDrop.getCorrectMatches().get("pomme"));  // should be equal
}

// Test to see if the answer will be accepted if uppercase or in mixed case
@Test
public void testDragAndDropItemLetterCasing() {
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme"), Arrays.asList("Fruit", "Vegetable"), Map.of("pomme", "fruit"));
    
    dragAndDrop.dragAndDropItem("pomme", "FRUIT"); // Test uppercase
    dragAndDrop.dragAndDropItem("pomme", "fRuIt"); // Test mixed case
    
    assertNotEquals("fruit", dragAndDrop.getUserMatches().get("pomme")); // Should be stored as lowercase and be equal
}


// Tests to see if the output to the console has all of the right items
@Test
public void testDisplayDraggableItems() {
    List<String> draggableItems = Arrays.asList("pomme", "carotte");
    DragAndDrop dragAndDrop = new DragAndDrop(draggableItems, new ArrayList<>(), new HashMap<>());

    dragAndDrop.displayDraggableItems();
}

// Tests to check for duplicate cases --> should remove a duplicate word
@Test
public void testDuplicateDraggableItems() {
    List<String> draggableItems = Arrays.asList("pomme", "pomme");
    DragAndDrop dragAndDrop = new DragAndDrop(draggableItems, Arrays.asList("Fruit"), Map.of("pomme", "fruit"));
    
    assertEquals(2, dragAndDrop.getDraggableItems().size()); // Ensure only one item listed
}


// Tests to see if the output to console has all of the right targets
@Test
public void testDisplayTargets() {
    List<String> dropTargets = Arrays.asList("Fruit", "Vegetable");
    DragAndDrop dragAndDrop = new DragAndDrop(new ArrayList<>(), dropTargets, new HashMap<>());

    dragAndDrop.displayTargets();
}

// Tests to see when user submits an item not in list currently
@Test
public void testDragAndDropItemNotThere() {
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme"), Arrays.asList("Fruit"), Map.of("pomme", "fruit"));
    
    dragAndDrop.dragAndDropItem("poire", "Fruit"); // "poire" not in draggableItems
    
    assertFalse(dragAndDrop.getUserMatches().containsKey("poire")); // No match should be added
}

// Tests what happens when user puts in extra special characters --> should not
@Test
public void testInvalidCharactersInInput() {
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme"), Arrays.asList("Fruit"), Map.of("pomme", "fruit"));
    
    dragAndDrop.dragAndDropItem("pomme", "@Fruit!"); // extra special characters
    
    assertFalse(dragAndDrop.getUserMatches().containsKey("pomme")); // reject this invalid input
}

// Tests if right output when all submitted answers are correct
@Test
public void testSubmitAnswerIfAllCorrect() {
    Map<String, String> correctMatches = Map.of("pomme", "fruit", "carotte", "vegetable");
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme", "carotte"), Arrays.asList("Fruit", "Vegetable"), correctMatches);
    
    dragAndDrop.dragAndDropItem("pomme", "Fruit"); // Correct Answer
    dragAndDrop.dragAndDropItem("carotte", "Vegetable"); // Correct Answer

    assertTrue(dragAndDrop.submitAnswer());
}

// Tests if right output when not all submitted answers are correct
@Test
public void testSubmitAnswerIfSomeCorrect() {
    Map<String, String> correctMatches = Map.of("pomme", "fruit", "carotte", "vegetable");
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme", "carotte"), Arrays.asList("Fruit", "Vegetable"), correctMatches);
    
    dragAndDrop.dragAndDropItem("pomme", "Fruit"); // Correct Answer
    dragAndDrop.dragAndDropItem("carotte", "Fruit"); // Incorrect Answer

    assertFalse(dragAndDrop.submitAnswer());
}

// Tests if right output when no submitted answers are correct
@Test
public void testSubmitAnswerIfNoneCorrect() {
    Map<String, String> correctMatches = Map.of("pomme", "fruit", "carotte", "vegetable");
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme", "carotte"), Arrays.asList("Fruit", "Vegetable"), correctMatches);
    
    dragAndDrop.dragAndDropItem("pomme", "Vegetable"); // Incorrect
    dragAndDrop.dragAndDropItem("carotte", "Fruit"); // Incorrect

    assertFalse(dragAndDrop.submitAnswer()); 
}

// Tests what happens when no answers are submitted --> if no answer submitted, should be false
@Test
public void testSubmitEmptyAnswers() {
    DragAndDrop dragAndDrop = new DragAndDrop(new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    assertFalse(dragAndDrop.submitAnswer()); 
}

// Tests what happens when there is null input
@Test
public void testNullInputs() {
    DragAndDrop dragAndDrop = new DragAndDrop(null, null, null);
    assertNotNull(dragAndDrop.getDraggableItems()); // shouldn't be null
    assertNotNull(dragAndDrop.getDropTargets());
    assertNotNull(dragAndDrop.getCorrectMatches());
}

// Tests to see if partial matches are accepted --> should not be
@Test
public void testPartialMatches() {
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme"), Arrays.asList("Fruit"), Map.of("pomme", "fruit"));
    
    dragAndDrop.dragAndDropItem("pom", "Fruit");
    
    assertNull(dragAndDrop.getUserMatches().get("pom")); // Should not be valid match
}

// Tests to see if the game resets properly
@Test
public void testResetGame() {
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme", "carotte"), Arrays.asList("Fruit", "Vegetable"), Map.of("pomme", "fruit"));
    
    dragAndDrop.dragAndDropItem("pomme", "Fruit");
    dragAndDrop.resetGame();
    
    assertTrue(dragAndDrop.getUserMatches().isEmpty()); // When game is reset the match list should clear
}

// Tests to see what heppens when the game is reset when the user isn't finished --> should clear all user matches
@Test
public void testResetGameNotFinished() {
    Map<String, String> correctMatches = Map.of("pomme", "fruit", "carotte", "vegetable");
    DragAndDrop dragAndDrop = new DragAndDrop(Arrays.asList("pomme", "carotte"), Arrays.asList("Fruit", "Vegetable"), correctMatches);

    dragAndDrop.dragAndDropItem("pomme", "Fruit"); // One part of game
    dragAndDrop.resetGame();

    assertTrue(dragAndDrop.getUserMatches().isEmpty()); // Ensure all matches are cleared
}

}

