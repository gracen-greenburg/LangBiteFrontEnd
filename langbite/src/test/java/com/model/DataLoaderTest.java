package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DataLoaderTest {

    @Test
    public void testUsers() throws Exception {

        // Load User.json file as InputStream
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("User.json");
        assertNotNull("Json file not found", inputStream);

        // Parse JSON data
        JSONParser jsonParser = new JSONParser();
        JSONObject rootObject;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            rootObject = (JSONObject) jsonParser.parse(reader); 
        }

        assertNotNull("Root JSON object is null", rootObject);

        // Get the users array from the root object
        JSONArray usersArray = (JSONArray) rootObject.get("users");
        assertNotNull("Users array is null", usersArray);
        assertFalse("Users array is empty", usersArray.isEmpty());

        // Access the first user check  fields
        JSONObject firstUser = (JSONObject) usersArray.get(0);
        assertNotNull("First user is null", firstUser);

        // Verify fields in the first user
        assertTrue("User ID is missing", firstUser.containsKey("id"));
        assertTrue("Username is missing", firstUser.containsKey("username"));
        assertTrue("Email is missing", firstUser.containsKey("email"));
        assertTrue("First name is missing", firstUser.containsKey("first_name"));
        assertTrue("Last name is missing", firstUser.containsKey("last_name"));
        assertTrue("Password is missing", firstUser.containsKey("password"));
        assertTrue("Progress is missing", firstUser.containsKey("progress"));

        // Validate specific values 
        assertEquals("Expected username mismatch", "loumur", firstUser.get("username"));
        assertEquals("Expected email mismatch", "lou@mur.com", firstUser.get("email"));
        assertEquals("Expected ID mismatch", "d4d549ce-83e0-482b-ae25-c90b141d3e38", firstUser.get("id"));
    }

    @Test
    public void testCurrentWords() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CurrentWords.json");
        assertNotNull("CurrentWords.json file not found", inputStream);

        JSONParser jsonParser = new JSONParser();
        JSONArray wordsArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            wordsArray = (JSONArray) jsonParser.parse(reader);
        }

        assertNotNull("Words array is null", wordsArray);
        assertFalse("Words array is empty", wordsArray.isEmpty());

        JSONObject firstWord = (JSONObject) wordsArray.get(0);
        assertTrue("Image key is missing", firstWord.containsKey("image"));
        assertTrue("Gender key is missing", firstWord.containsKey("gender"));
        assertTrue("English key is missing", firstWord.containsKey("english"));
        assertTrue("French key is missing", firstWord.containsKey("french"));
        assertTrue("Part of Speech key is missing", firstWord.containsKey("partOfSpeech"));
    }

    @Test
    public void testGrammar() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Grammar.json");
        assertNotNull("Grammar.json file not found", inputStream);

        JSONParser jsonParser = new JSONParser();
        JSONArray grammarArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            grammarArray = (JSONArray) jsonParser.parse(reader);
        }

        assertNotNull("Grammar array is null", grammarArray);
        assertFalse("Grammar array is empty", grammarArray.isEmpty());

        JSONObject firstRule = (JSONObject) grammarArray.get(0);
        assertTrue("Rule key is missing", firstRule.containsKey("rule"));
        assertTrue("Rule-text key is missing", firstRule.containsKey("rule-text"));
        assertTrue("Explanation key is missing", firstRule.containsKey("explanation"));
        assertTrue("Examples key is missing", firstRule.containsKey("examples"));
    }

    @Test
    public void testWords() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Words.json");
        assertNotNull("Words.json file not found", inputStream);

        JSONParser jsonParser = new JSONParser();
        JSONArray wordsArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            wordsArray = (JSONArray) jsonParser.parse(reader);
        }

        assertNotNull("Words array is null", wordsArray);
        assertFalse("Words array is empty", wordsArray.isEmpty());

        JSONObject firstWord = (JSONObject) wordsArray.get(0);
        assertTrue("Image key is missing", firstWord.containsKey("image"));
        assertTrue("Gender key is missing", firstWord.containsKey("gender"));
        assertTrue("English key is missing", firstWord.containsKey("english"));
        assertTrue("French key is missing", firstWord.containsKey("french"));
        assertTrue("Part of Speech key is missing", firstWord.containsKey("partOfSpeech"));
    }

    @Test
    public void testCurrentWordsStructure() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CurrentWords.json");
        assertNotNull("CurrentWords.json file not found", inputStream);

        JSONParser jsonParser = new JSONParser();
        JSONArray wordsArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            wordsArray = (JSONArray) jsonParser.parse(reader);
        }

        assertNotNull("Words array is null", wordsArray);
        assertFalse("Words array is empty", wordsArray.isEmpty());

        JSONObject firstWord = (JSONObject) wordsArray.get(0);

        // Check for field existence
        assertTrue("Image key is missing", firstWord.containsKey("image"));
        assertTrue("Mastered key is missing", firstWord.containsKey("mastered"));
        assertTrue("Attempts key is missing", firstWord.containsKey("attempts"));
        
        // Validate data types
        assertTrue("Mastered should be a boolean", firstWord.get("mastered") instanceof Boolean);
        assertTrue("Attempts should be a number", firstWord.get("attempts") instanceof Long);
        
        // Check non-negative attempts count
        long attempts = (Long) firstWord.get("attempts");
        assertTrue("Attempts should be non-negative", attempts >= 0);
    }

    @Test
    public void testGrammarQuestions() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Grammar.json");
        assertNotNull("Grammar.json file not found", inputStream);

        JSONParser jsonParser = new JSONParser();
        JSONArray grammarArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            grammarArray = (JSONArray) jsonParser.parse(reader);
        }

        assertNotNull("Grammar array is null", grammarArray);
        assertFalse("Grammar array is empty", grammarArray.isEmpty());

        JSONObject firstRule = (JSONObject) grammarArray.get(0);
        JSONArray questionsArray = (JSONArray) firstRule.get("questions");

        // Check that questions array exists and is not empty
        assertNotNull("Questions array is missing", questionsArray);
        assertFalse("Questions array is empty", questionsArray.isEmpty());

        // Check structure of the first question
        JSONObject firstQuestion = (JSONObject) questionsArray.get(0);
        assertTrue("Question text is missing", firstQuestion.containsKey("text"));
        assertTrue("Question options are missing", firstQuestion.containsKey("options"));
        assertTrue("Correct answer index is missing", firstQuestion.containsKey("correctAnswer"));

        // Validate types
        assertTrue("Text should be a string", firstQuestion.get("text") instanceof String);
        assertTrue("Options should be an array", firstQuestion.get("options") instanceof JSONArray);
        assertTrue("Correct answer index should be a number", firstQuestion.get("correctAnswer") instanceof Long);

        // Validate that options array is non-empty
        JSONArray options = (JSONArray) firstQuestion.get("options");
        assertFalse("Options array should not be empty", options.isEmpty());
    }

    @Test
    public void testWordsFieldValues() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Words.json");
        assertNotNull("Words.json file not found", inputStream);

        JSONParser jsonParser = new JSONParser();
        JSONArray wordsArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            wordsArray = (JSONArray) jsonParser.parse(reader);
        }

        assertNotNull("Words array is null", wordsArray);
        assertFalse("Words array is empty", wordsArray.isEmpty());

        JSONObject firstWord = (JSONObject) wordsArray.get(0);

        // Validate correctCount <= attempts
        long correctCount = (Long) firstWord.get("correctCount");
        long attempts = (Long) firstWord.get("attempts");
        assertTrue("Correct count should not exceed attempts", correctCount <= attempts);

        // Validate gender values
        String gender = (String) firstWord.get("gender");
        List<String> validGenders = Arrays.asList("masculine", "feminine", "masculine plural", "feminine plural", "none");
        assertTrue("Invalid gender value", validGenders.contains(gender));
    }

    
    

    @Test
    public void furtherTestWordsFieldValues() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Words.json");
        assertNotNull("Words.json file not found", inputStream);
    
        JSONParser jsonParser = new JSONParser();
        JSONArray wordsArray;
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            wordsArray = (JSONArray) jsonParser.parse(reader);
        }
    
        assertNotNull("Words array is null", wordsArray);
        assertFalse("Words array is empty", wordsArray.isEmpty());
    
        List<String> validGenders = Arrays.asList("masculine", "feminine", "masculine plural", "feminine plural", "none");
        boolean hasInconsistencies = false;
    
        // Collect all inconsistencies
        for (int i = 0; i < wordsArray.size(); i++) {
            JSONObject word = (JSONObject) wordsArray.get(i);
    
            long correctCount = (Long) word.get("correctCount");
            long attempts = (Long) word.get("attempts");
    
            // Check for inconsistency and mark if found
            if (correctCount > attempts) {
                System.out.println("Inconsistent entry at index " + i + ": " + word.toJSONString());
                hasInconsistencies = true;
            }
    
            // Validate gender values
            String gender = (String) word.get("gender");
            assertTrue("Invalid gender value for entry at index " + i, validGenders.contains(gender));
        }
    
        // Fail the test if any inconsistencies were found
        assertFalse("One or more entries have correctCount exceeding attempts. Check console for details.", hasInconsistencies);
    }

}