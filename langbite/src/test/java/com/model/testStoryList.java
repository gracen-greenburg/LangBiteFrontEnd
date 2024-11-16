package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testStoryList {

    private StoryList storyList;

    @Before
    public void setUp() {
        // Prepare a list of mock stories
        List<Story> mockStories = new ArrayList<>();

        Story story1 = new Story("The Lion and the Mouse", "A story about kindness and helping each other.", List.of("lion", "mouse", "help"));
        Story story2 = new Story("The Tortoise and the Hare", "A story about patience and persistence.", List.of("tortoise", "hare", "race"));
        mockStories.add(story1);
        mockStories.add(story2);

        // Mock DataLoader to return the mock stories
        DataLoader.setMockStories(mockStories);

        // Initialize StoryList which should load the mock stories
        storyList = new StoryList();
    }

    // Test that the StoryList constructor loads stories correctly
    @Test
    public void testConstructorLoadsStories() {
        List<Story> loadedStories = storyList.getAllStories();
        assertNotNull("Story list should not be null", loadedStories);
        assertEquals("Story list should contain 2 stories", 2, loadedStories.size());
    }

    // Test getStoryByTitle with an existing title
    @Test
    public void testGetStoryByTitle_Found() {
        Story foundStory = storyList.getStoryByTitle("The Lion and the Mouse");
        assertNotNull("Story should be found", foundStory);
        assertEquals("The Lion and the Mouse", foundStory.getTitle());
        assertEquals("A story about kindness and helping each other.", foundStory.getText());
    }

    // Test getStoryByTitle with a non-existing title
    @Test
    public void testGetStoryByTitle_NotFound() {
        Story notFoundStory = storyList.getStoryByTitle("Non-Existent Story");
        assertNull("Story should not be found", notFoundStory);
    }

    // Test adding a new story to the list
    @Test
    public void testAddStory() {
        Story newStory = new Story("The Fox and the Grapes", "A story about jealousy and letting go.", List.of("fox", "grapes"));
        storyList.addStory(newStory);

        List<Story> updatedStories = storyList.getAllStories();
        assertEquals("Story list should contain 3 stories", 3, updatedStories.size());
        assertEquals("The Fox and the Grapes", updatedStories.get(2).getTitle());
    }

    // Test displayStories method (output only, does not return anything)
    @Test
    public void testDisplayStories() {
        storyList.displayStories();
    }

    // Test reloading stories
    @Test
    public void testReloadStories() {
        // Change the mock data to simulate a reload
        List<Story> newMockStories = List.of(
            new Story("The Ant and the Grasshopper", "A story about hard work and preparation.", List.of("ant", "grasshopper"))
        );
        DataLoader.setMockStories(newMockStories);

        // Reload the stories in the StoryList
        storyList.reloadStories();

        List<Story> reloadedStories = storyList.getAllStories();
        assertEquals("Story list should contain 1 story after reload", 1, reloadedStories.size());
        assertEquals("The Ant and the Grasshopper", reloadedStories.get(0).getTitle());
    }
}
