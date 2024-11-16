package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Scanner;

import static org.junit.Assert.*;

public class LanguageListTest {

    private JSONObject currentUser;
    private LanguageList languageList;

    

    @Before
    public void setUp() {
    // Initialize a mock user with no languages initially
        currentUser = new JSONObject();
        currentUser.put("id", "test-user-id");
        currentUser.put("first_name", "Test");
        currentUser.put("last_name", "User");
        currentUser.put("username", "testuser");
        currentUser.put("languages", new JSONArray());

        JSONObject progress = new JSONObject();
        progress.put("grammarRulesCompletionPercentage", 0L);
        progress.put("connectorWordsCompletionPercentage", 0L);
        progress.put("mostCommonWordsCompletionPercentage", 0L);
        currentUser.put("progress", progress);

        languageList = new LanguageList(currentUser);  
    }

    @Test
    public void testAddLanguage() {
        String simulatedInput = "1\n4\n";  
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        languageList.displayOptions(scanner);

        JSONArray languages = (JSONArray) currentUser.get("languages");
        assertTrue("French should be added to the user's language list", languages.contains("French"));
    }

    @Test
    public void testAddLanguageAlreadyAdded() {
        JSONArray languages = (JSONArray) currentUser.get("languages");
        languages.add("French");
        currentUser.put("languages", languages);

        String simulatedInput = "1\n4\n";  // Attempt to Add Language then Logout
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        languageList.displayOptions(scanner);

        assertEquals("French should still be the only language in the list", 1, languages.size());
    }

    @Test
    public void testRemoveLanguageLanguageExists() {
        JSONArray languages = (JSONArray) currentUser.get("languages");
        languages.add("French");
        currentUser.put("languages", languages);

        String simulatedInput = "2\nFrench\n4\n";  // Remove Language then Logout
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        languageList.displayOptions(scanner);

        assertFalse("French should be removed from the user's language list", languages.contains("French"));
    }

    @Test
    public void testRemoveLanguageLanguageDoesNotExist() {
        String simulatedInput = "2\nSpanish\n4\n";  // Attempt to Remove non-existing Language then Logout
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        languageList.displayOptions(scanner);

        JSONArray languages = (JSONArray) currentUser.get("languages");
        assertTrue("The languages list should still be empty", languages.isEmpty());
    }

    @Test
    public void testOpenLanguageSupported() {
        JSONArray languages = (JSONArray) currentUser.get("languages");
        languages.add("French");
        currentUser.put("languages", languages);

        String simulatedInput = "3\nFrench\n4\n";  // Open Language then Logout
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        languageList.displayOptions(scanner);

        assertTrue("French should still be in the list", languages.contains("French"));
        assertEquals("Module should be started once", 1, ((TestableLanguageList) languageList).getModuleStartCount());
    }

    @Test
    public void testOpenLanguageUnsupported() {
        JSONArray languages = (JSONArray) currentUser.get("languages");
        languages.add("German");
        currentUser.put("languages", languages);

        String simulatedInput = "3\nGerman\n4\n";  // Attempt to Open Unsupported Language, then Logout
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        languageList.displayOptions(scanner);

        assertTrue("The unsupported language should still be in the list", languages.contains("German"));
    }

    // Custom LanguageList to avoid invoking real LanguageModule
    private class TestableLanguageList extends LanguageList {
        private int moduleStartCount = 0;

        public TestableLanguageList(JSONObject currentUser) {
            super(currentUser);
        }

        @Override
        protected LanguageModule createLanguageModule() {
            return new LanguageModule(currentUser) {
                @Override
                public void startModule(Scanner scanner) {
                    moduleStartCount++;
                }
            };
        }

        public int getModuleStartCount() {
            return moduleStartCount;
        }
    }
}
