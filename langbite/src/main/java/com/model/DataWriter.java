package com.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * DataWrite 
 * 
 * @author LangBite Team
 */
public class DataWriter extends DataConstants {

    /*
     * DataWriter takes information given by the user and inputs
     * it into a JSON file, and stores that information within
     */

    /*
     * Class saveUsers saves new users to the Ussers JSON file
     */
    // public static void saveUsers() {
    //     Users users = Users.getInstance();
    //     ArrayList<User> userList = users.getUsers();
    //     JSONArray jsonUsers = new JSONArray();

    //     // Create JSON objects for each user
    //     for (User user : userList) {
    //         jsonUsers.add(getUserJSON(user));
    //     }

    //     // (FileWriter file = new FileWriter(USER_FILE_NAME))

    //     // Write JSON file
    //     try {

    //         String path = getFileWritingPath(USER_FILE_NAME, USER_FILE_NAME_JUNIT);
    //         FileWriter writer = new FileWriter(path);

    //         writer.write(jsonUsers.toJSONString());
    //         writer.flush();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /*
     * Class getUserJSON converts a User object into a JSON Object
     */
    private static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_FIRST_NAME, user.getFirstName());
        userDetails.put(USER_LAST_NAME, user.getLastName());
        userDetails.put(USER_USERNAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());

        // Save progress as percentages
        ProgressTracker tracker = user.getProgressTracker();
        userDetails.put(USER_PROGRESS, new JSONObject(tracker.toProgressPercentageData()));
        return userDetails;
    }

    // public static void updateCurrentModule(String username, int newModule) {
    //     // Load users as JSONArray
    //     JSONArray usersArray = DataLoader.loadUsersJSON();

    //     // Find and update the specified user
    //     for (Object obj : usersArray) {
    //         JSONObject userJSON = (JSONObject) obj;
    //         String jsonUsername = (String) userJSON.get(DataConstants.USER_USERNAME);

    //         if (jsonUsername.equals(username)) {
    //             userJSON.put(DataConstants.USER_CURRENT_MODULE, newModule); // Update currentModule
    //             break;
    //         }
    //     }

    //     // Write the updated JSON array back to user.json
    //     try (FileWriter file = new FileWriter(DataConstants.USER_FILE_NAME)) {
    //         file.write(usersArray.toJSONString());
    //         file.flush();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    private static String getFileWritingPath(String PATH_NAME, String JUNIT_PATH_NAME) {
        try {
            if (isJUnitTest()) {
                URI url = DataLoader.class.getResource(JUNIT_PATH_NAME).toURI();
                return url.getPath();
            } else {
                return PATH_NAME;
            }
        } catch (Exception e) {
            System.out.println("Difficulty getting resource path");
            return "";
        }
    }

    public static JSONObject mapToJSONObject(Map<String, Integer> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    public static void saveWordToCurrentWords(Word word) {
        JSONArray wordsArray = loadCurrentWordsJSON();

        // Add new word if it does not already exist in the JSON array
        boolean exists = wordsArray.stream().anyMatch(obj -> ((JSONObject) obj).get("french").equals(word.getFrench()));
        if (!exists) {
            wordsArray.add(getWordJSON(word));
        }

        try (FileWriter file = new FileWriter(DataConstants.CURRENT_WORDS_FILE)) {
            file.write(formatJSON(wordsArray));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateWordsFile(Word wordToUpdate) {
        try {
            FileReader reader = new FileReader(DataConstants.WORD_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray wordsArray = (JSONArray) parser.parse(reader);

            // Update mastered and correctCount fields for the specified word
            for (Object obj : wordsArray) {
                JSONObject wordJSON = (JSONObject) obj;
                if (wordJSON.get("french").equals(wordToUpdate.getFrench())) {
                    wordJSON.put("mastered", wordToUpdate.isMastered());
                    wordJSON.put("correctCount", wordToUpdate.getCorrectCount());
                    break;
                }
            }

            try (FileWriter file = new FileWriter(DataConstants.WORD_FILE_NAME)) {
                file.write(formatJSON(wordsArray));
                file.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getWordJSON(Word word) {
        JSONObject wordDetails = new JSONObject();
        wordDetails.put(DataConstants.WORD_ENGLISH, word.getEnglish());
        wordDetails.put(DataConstants.WORD_FRENCH, word.getFrench());
        wordDetails.put(DataConstants.WORD_GENDER, word.getGender());
        wordDetails.put(DataConstants.WORD_IMAGE, word.getImage());
        wordDetails.put(DataConstants.WORD_MASTERED, word.isMastered());
        wordDetails.put(DataConstants.WORD_CORRECT_COUNT, word.getCorrectCount());
        wordDetails.put(DataConstants.WORD_ATTEMPTS, word.getAttempts());
        wordDetails.put(DataConstants.WORD_PART_OF_SPEECH, word.getPartOfSpeech());
        wordDetails.put(DataConstants.WORD_TYPE, word.getType());
        return wordDetails;
    }

    private static JSONArray loadCurrentWordsJSON() {
        JSONArray wordsArray = new JSONArray();
        try (FileReader reader = new FileReader(DataConstants.CURRENT_WORDS_FILE)) {
            JSONParser jsonParser = new JSONParser();
            wordsArray = (JSONArray) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return wordsArray;
    }

    private static String formatJSON(JSONArray jsonArray) {
        StringBuilder formattedJSON = new StringBuilder("[\n");
        int indent = 2; // Indentation level

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            formattedJSON.append(" ".repeat(indent))
                    .append(jsonObject.toJSONString());
            if (i < jsonArray.size() - 1) {
                formattedJSON.append(",\n");
            }
        }
        formattedJSON.append("\n]");
        return formattedJSON.toString();
    }

    public static void saveWordsToCurrentWords(ArrayList<Word> words) {
        JSONArray wordsArray = loadCurrentWordsJSON();

        // Append new words without duplicates
        for (Word word : words) {
            JSONObject wordJSON = getWordJSON(word);
            boolean exists = wordsArray.stream()
                    .anyMatch(obj -> ((JSONObject) obj).get("french").equals(word.getFrench()));

            if (!exists) {
                wordsArray.add(wordJSON);
            }
        }

        // Save the updated list of words
        try (FileWriter file = new FileWriter(DataConstants.CURRENT_WORDS_FILE)) {
            file.write(formatJSON(wordsArray));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGrammarProgress(List<GrammarRule> rules) {
        JSONArray grammarRulesArray = new JSONArray();

        for (GrammarRule rule : rules) {
            JSONObject ruleJSON = new JSONObject();
            ruleJSON.put("rule-text", rule.getRuleText());
            ruleJSON.put("explanation", rule.getExplanation());
            ruleJSON.put("examples", rule.getExamples());
            ruleJSON.put("attempts", rule.getAttempts());
            ruleJSON.put("correctAttempts", rule.getCorrectAttempts());

            // Save questions
            JSONArray questionsArray = new JSONArray();
            for (GrammarQuestion question : rule.getQuestions()) {
                JSONObject questionJSON = new JSONObject();
                questionJSON.put("text", question.getText());

                JSONArray optionsArray = new JSONArray();
                for (String option : question.getOptions()) {
                    optionsArray.add(option);
                }
                questionJSON.put("options", optionsArray);
                questionJSON.put("correctAnswer", question.getCorrectAnswer());
                questionsArray.add(questionJSON);
            }
            ruleJSON.put("questions", questionsArray);

            grammarRulesArray.add(ruleJSON);
        }

        // Write the updated rules back to the JSON file
        try (FileWriter file = new FileWriter(GRAMMAR_RULES_FILE_NAME)) {
            file.write(grammarRulesArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateGrammarRuleStatus(String ruleText, boolean learned) {
        JSONArray grammarRulesArray = DataLoader.loadGrammarRulesJSON();

        for (Object obj : grammarRulesArray) {
            JSONObject ruleJSON = (JSONObject) obj;
            if (ruleJSON.get("rule-text").equals(ruleText)) {
                ruleJSON.put("learned", learned);
                break;
            }
        }

        try (FileWriter file = new FileWriter(GRAMMAR_RULES_FILE_NAME)) {
            file.write(grammarRulesArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUserProgress(User user) {
        try (FileReader reader = new FileReader("user.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray usersArray = (JSONArray) jsonObject.get("users");

            for (Object obj : usersArray) {
                JSONObject userObj = (JSONObject) obj;
                if (userObj.get("username").equals(user.getUsername())) {
                    JSONObject currentProgress = (JSONObject) userObj.get("progress");
                    Map<String, Integer> newProgress = user.getProgressTracker().toProgressPercentageData();
                    JSONObject newProgressJSON = mapToJSONObject(newProgress);

                    // Update only if new progress values are higher
                    for (Object key : newProgressJSON.keySet()) {
                        String keyStr = (String) key;
                        int newValue = ((Long) newProgressJSON.get(keyStr)).intValue();
                        int currentValue = ((Long) currentProgress.getOrDefault(keyStr, 0L)).intValue();

                        if (newValue > currentValue) {
                            currentProgress.put(keyStr, newValue);
                        }
                    }
                    break;
                }
            }

            try (FileWriter writer = new FileWriter("user.json")) {
                writer.write(jsonObject.toJSONString());
                writer.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsers(JSONArray users) {
        try {
            JSONObject data = new JSONObject();
            data.put("users", users);

            try (FileWriter file = new FileWriter(USER_FILE)) {
                file.write(data.toJSONString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DIFFERERENT SAVEUSERS METHOD, IF NECESSARY CAN DELETE
    public static void saveUsers() {
        Users users = Users.getInstance();
        ArrayList<User> userList = users.getUsers();
        JSONArray jsonUsers = new JSONArray();
    
        // Convert each User to JSON and add to jsonUsers array
        for (User user : userList) {
            jsonUsers.add(getUserJSON(user));
        }
    }

    public static void saveUser(JSONObject updatedUser) {
        try {
            JSONArray users = DataLoader.loadUsers();
            String userId = (String) updatedUser.get("id");
            boolean userFound = false;
    
            for (int i = 0; i < users.size(); i++) {
                JSONObject user = (JSONObject) users.get(i);
                if (user.get("id").equals(userId)) {
                    JSONObject currentProgress = (JSONObject) user.get("progress");
                    JSONObject newProgress = (JSONObject) updatedUser.get("progress");
    
                    // Update only if new progress values are higher
                    for (Object key : newProgress.keySet()) {
                        String keyStr = (String) key;
                        Number newValueNumber = (Number) newProgress.get(keyStr);
                        int newValue = newValueNumber.intValue();
    
                        Number currentValueNumber = (Number) currentProgress.getOrDefault(keyStr, 0L);
                        int currentValue = currentValueNumber.intValue();
    
                        if (newValue > currentValue) {
                            currentProgress.put(keyStr, newValue);
                        }
                    }
    
                    // Update the user object with the new progress
                    user.put("progress", currentProgress);
                    userFound = true;
                    break;
                }
            }
    
            if (!userFound) {
                users.add(updatedUser);
            }
    
            try (FileWriter file = new FileWriter(USER_FILE)) {
                JSONObject data = new JSONObject();
                data.put("users", users);
                file.write(data.toJSONString());
                file.flush();
            }
    
            System.out.println("User data saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving user data.");
            e.printStackTrace();
        }
    }
    
}