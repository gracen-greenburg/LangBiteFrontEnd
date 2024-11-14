package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.model.DragandDrop.DragAndDrop;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/*
 * DataLoader
 * 
 * @author LangBite Team
 */
public class DataLoader extends DataConstants {
    /*
     * DataLoader takes the information from the JSON files and 
     * loads them in to the program. For example, the String 
     * englishWord variable information is drawn from the wordJSON 
     * file, as well as the frenchWord and the partOfSpeech Words
     */

     /*
      * Class loadUsers loads the users from the JSON file
      */


    


    // public static ArrayList<User> loadUsers() {
    //     ArrayList<User> users = new ArrayList<>();
    //     BufferedReader reader = getReaderFromFile(USER_FILE_NAME, USER_FILE_NAME_JUNIT);

    //     try {
    //         // FileReader reader = new FileReader(USER_FILE_NAME);
    //         JSONParser parser = new JSONParser();
    //         JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

    //         for (int i = 0; i < usersJSON.size(); i++) {
    //             JSONObject userJSON = (JSONObject) usersJSON.get(i);
    //             String firstName = (String) userJSON.get(USER_FIRST_NAME);
    //             String lastName = (String) userJSON.get(USER_LAST_NAME);
    //             String username = (String) userJSON.get(USER_USERNAME);
    //             String password = (String) userJSON.get(USER_PASSWORD);
    //             int progress = ((Long)userJSON.get(USER_PROGRESS)).intValue();
    //             int currentModule = ((Long)userJSON.get(USER_CURRENT_MODULE)).intValue();


    //             users.add(new User(firstName, lastName, username, password,progress,currentModule));
    //         }
    //         // return users;
    //         reader.close();

    //     } catch (IOException | ParseException e) {
    //         e.printStackTrace();
    //     }

    //     return users;
    //     // return null;
    // }

    // load lessons from the json file
    public static ArrayList<Lesson> loadLessons() {
        ArrayList<Lesson> lessons = new ArrayList<>();

        try {
            FileReader reader = new FileReader(LESSONS_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray lessonsJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < lessonsJSON.size(); i++) {
                JSONObject lessonJSON = (JSONObject) lessonsJSON.get(i);
                
                // lesson fields
                String lessonName = (String) lessonJSON.get(LESSON_NAME);
                int moduleNumber = ((Long) lessonJSON.get(LESSON_MODULE_NUMBER)).intValue();
                String grammar = (String) lessonJSON.get(LESSON_GRAMMAR);
                String culturalNotes = (String) lessonJSON.get(LESSON_CULTURAL_NOTES);

                // Vocabulary
                List<Word> vocabulary = new ArrayList<>();
                JSONArray vocabArray = (JSONArray) lessonJSON.get(LESSON_VOCABULARY);
                for (int j = 0; j < vocabArray.size(); j++) {
                    JSONObject wordJSON = (JSONObject) vocabArray.get(j);
                    String word = (String) wordJSON.get(VOCAB_WORD);
                    String meaning = (String) wordJSON.get(VOCAB_MEANING);
                    String gender = (String) wordJSON.get(WORD_GENDER);
                    String image = (String) wordJSON.get(WORD_IMAGE);
                    boolean mastered = (Boolean) wordJSON.get(WORD_MASTERED);
                    int correctCount =  ((Long) wordJSON.get(WORD_CORRECT_COUNT)).intValue();
                    int attempts = ((Long) wordJSON.get(WORD_ATTEMPTS)).intValue();
                    String partOfSpeech = (String) wordJSON.get(WORD_PART_OF_SPEECH);
                    String type = (String) wordJSON.get(WORD_TYPE);

                    vocabulary.add(new Word(word, meaning, gender, image, mastered, correctCount, attempts, partOfSpeech, type));
                }

                // Questions
                List<MutipleChoice> mcQuestions = new ArrayList<>();
                List<FillinTheBlank> fillInTheBlankQuestions = new ArrayList<>();
                List<DragAndDrop> dragAndDropQuestions = new ArrayList<>();

                JSONArray questionsArray = (JSONArray) lessonJSON.get(LESSON_QUESTIONS);
                for (int k = 0; k < questionsArray.size(); k++) {
                    JSONObject questionJSON = (JSONObject) questionsArray.get(k);
                    String type = (String) questionJSON.get(QUESTION_TYPE);

                    // Handle multiple choice questions
                    if (type.equals(QUESTION_MULTIPLE_CHOICE)) {
                        String questionText = (String) questionJSON.get(MC_QUESTION);
                        JSONArray optionsArray = (JSONArray) questionJSON.get(MC_OPTIONS);
                        List<String> options = new ArrayList<>();
                        for (int l = 0; l < optionsArray.size(); l++) {
                            options.add((String) optionsArray.get(l));
                        }
                        char correctAnswer = ((String) questionJSON.get(MC_CORRECT_ANSWER)).charAt(0);
                        mcQuestions.add(new MutipleChoice(questionText, options, correctAnswer));
                    }

                    // Handle fill-in-the-blank questions
                    else if (type.equals(QUESTION_FILL_IN_THE_BLANK)) {
                        String sentence = (String) questionJSON.get(FILL_BLANK_SENTENCE);
                        JSONArray correctAnswersArray = (JSONArray) questionJSON.get(FILL_BLANK_CORRECT_ANSWERS);
                        List<String> correctAnswers = new ArrayList<>();
                        for (int l = 0; l < correctAnswersArray.size(); l++) {
                            correctAnswers.add((String) correctAnswersArray.get(l));
                        }
                        fillInTheBlankQuestions.add(new FillinTheBlank(sentence, correctAnswers));
                    }

                    // Handle drag-and-drop questions
                    else if (type.equals(QUESTION_DRAG_AND_DROP)) {
                        JSONArray draggableItemsArray = (JSONArray) questionJSON.get(DRAG_DROP_ITEMS);
                        List<String> draggableItems = new ArrayList<>(draggableItemsArray);

                        JSONArray dropTargetsArray = (JSONArray) questionJSON.get(DRAG_DROP_TARGETS);
                        List<String> dropTargets = new ArrayList<>(dropTargetsArray);

                        JSONObject correctMatchesJSON = (JSONObject) questionJSON.get(DRAG_DROP_CORRECT_MATCHES);
                        Map<String, String> correctMatches = new HashMap<>();
                        for (Object key : correctMatchesJSON.keySet()) {
                            correctMatches.put((String) key, (String) correctMatchesJSON.get(key));
                        }
                        dragAndDropQuestions.add(new DragAndDrop(draggableItems, dropTargets, correctMatches));
                    }
                }
                

                lessons.add(new Lesson(lessonName, vocabulary, grammar, culturalNotes, moduleNumber, mcQuestions, fillInTheBlankQuestions, dragAndDropQuestions));
            }

            return lessons;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // // Load user data as JSONArray for JSON modifications
    // public static JSONArray loadUsersJSON() {
    //     JSONArray usersArray = new JSONArray();
    //     try (FileReader reader = new FileReader(DataConstants.USER_FILE_NAME)) {
    //         JSONParser jsonParser = new JSONParser();
    //         usersArray = (JSONArray) jsonParser.parse(reader);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return usersArray;
    // }


    // public static ArrayList<Story> loadStories() {
    //     ArrayList<Story> stories = new ArrayList<>();

    //     try {
    //         FileReader reader = new FileReader(STORY_FILE_NAME);
    //         JSONParser parser = new JSONParser();
    //         JSONArray storiesJSON = (JSONArray) parser.parse(reader);

    //         for (int i = 0; i < storiesJSON.size(); i++) {
    //             JSONObject storyJSON = (JSONObject) storiesJSON.get(i);
    //             String title = (String) storyJSON.get(STORY_TITLE);
    //             String text = (String) storyJSON.get(STORY_TEXT);
                
    //             // Parse word list from JSON
    //             JSONArray wordsArray = (JSONArray) storyJSON.get(STORY_WORDLIST);
    //             ArrayList<String> wordList = new ArrayList<>();
    //             for (Object word : wordsArray) {
    //                 wordList.add((String) word);
    //             }

    //             // Create a new Story object and add it to the list
    //             stories.add(new Story(title, text, wordList));
    //         }

    //         return stories;

    //     } catch (IOException | ParseException e) {
    //         e.printStackTrace();
    //     }

    //     return null;
    // }



    private static List<Word> mockData = null;
    private static boolean useMockData = false;

    public static JSONArray loadUsers() {
        if (useMockData && mockUsers != null) {
            return mockUsers; // Return mock data if set
        }

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(USER_FILE);
            JSONObject data = (JSONObject) parser.parse(reader);
            reader.close();
            return (JSONArray) data.get("users");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();  // Return an empty array if an error occurs
        }
    }

    // Load words from JSON file
    public static ArrayList<Word> loadWords() {

        // ensuring that it can load mock data as well
        if (useMockData && mockData != null) {
            return new ArrayList<>(mockData);
        }
        
        ArrayList<Word> words = new ArrayList<>();



        try {
            FileReader reader = new FileReader(WORD_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray wordsJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < wordsJSON.size(); i++) {
                JSONObject wordJSON = (JSONObject) wordsJSON.get(i);
                String english = (String) wordJSON.get(WORD_ENGLISH);
                String french = (String) wordJSON.get(WORD_FRENCH);
                String gender = (String) wordJSON.get(WORD_GENDER);
                String image = (String) wordJSON.get(WORD_IMAGE);
                boolean mastered = (Boolean) wordJSON.get(WORD_MASTERED);
                int correctCount = ((Long) wordJSON.get(WORD_CORRECT_COUNT)).intValue();
                int attempts = ((Long) wordJSON.get(WORD_ATTEMPTS)).intValue();
                String partOfSpeech = (String) wordJSON.get(WORD_PART_OF_SPEECH);
                String type = (String) wordJSON.get(WORD_TYPE);
                

                words.add(new Word(english, french, gender, image, mastered, correctCount, attempts, partOfSpeech, type));
            }

            return words;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    
	private static BufferedReader getReaderFromFile(String fileName, String jsonFileName){
		try {
			if(isJUnitTest()){
				InputStream inputStream = DataLoader.class.getResourceAsStream(jsonFileName);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				return new BufferedReader(inputStreamReader);
			} else {
				FileReader reader = new FileReader(fileName);
				return new BufferedReader(reader);
			}
		} catch(Exception e){
			System.out.println("Can't load");
			return null;
		}
			
	}


    static ArrayList<Word> loadCurrentWords() {
        ArrayList<Word> wordList = new ArrayList<>();
        try {
            FileReader reader = new FileReader(CURRENT_WORDS_FILE);
            JSONParser parser = new JSONParser();
            JSONArray wordsArray = (JSONArray) parser.parse(reader);

            for (Object obj : wordsArray) {
                JSONObject wordJSON = (JSONObject) obj;
                String english = (String) wordJSON.get("english");
                String french = (String) wordJSON.get("french");
                String gender = (String) wordJSON.get("gender");
                String image = (String) wordJSON.get("image");

                boolean mastered = Boolean.parseBoolean(wordJSON.get("mastered").toString());
                int correctCount = Integer.parseInt(wordJSON.get("correctCount").toString());
                int attempts = Integer.parseInt(wordJSON.get("attempts").toString());
                String partOfSpeech = (String) wordJSON.get("partOfSpeech");
                String type = (String) wordJSON.get("type");

                Word word = new Word(english, french, gender, image, mastered, correctCount, attempts, partOfSpeech, type);
                wordList.add(word);
            }
        } catch (IOException | ParseException e) {
            System.out.println("CurrentWords.json is empty or could not be found. Loading words from Words.json.");
        }
        return wordList;
    }

    static ArrayList<Word> loadNextWordsOfType(ArrayList<Word> currentWordList, String type) {
        ArrayList<Word> nextWords = new ArrayList<>();
        try {
            FileReader reader = new FileReader(WORD_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray wordsArray = (JSONArray) parser.parse(reader);

            for (Object obj : wordsArray) {
                JSONObject wordJSON = (JSONObject) obj;
                String english = (String) wordJSON.get("english");
                String french = (String) wordJSON.get("french");
                String gender = (String) wordJSON.get("gender");
                String image = (String) wordJSON.get("image");
                boolean mastered = (Boolean) wordJSON.get("mastered");
                int correctCount = ((Long) wordJSON.get("correctCount")).intValue();
                int attempts = ((Long) wordJSON.get("attempts")).intValue();
                String partOfSpeech = (String) wordJSON.get("partOfSpeech");
                String wordType = (String) wordJSON.get("type");

                if (type.equals(wordType) && !mastered) {
                    Word word = new Word(english, french, gender, image, mastered, correctCount, attempts, partOfSpeech, wordType);
                    if (!currentWordList.contains(word) && nextWords.size() < 5) {
                        nextWords.add(word);
                    }
                }

                if (nextWords.size() >= 5) {
                    break;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return nextWords;
    }

    public static ArrayList<GrammarRule> loadGrammarRules() {
        ArrayList<GrammarRule> grammarRules = new ArrayList<>();
    
        try {
            FileReader reader = new FileReader(GRAMMAR_RULES_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray rulesJSON = (JSONArray) parser.parse(reader);
    
            for (Object obj : rulesJSON) {
                JSONObject ruleJSON = (JSONObject) obj;
                String ruleText = (String) ruleJSON.get("rule-text");
                String explanation = (String) ruleJSON.get("explanation");
                String examples = (String) ruleJSON.get("examples");
                int attempts = ((Long) ruleJSON.get("attempts")).intValue();
                int correctAttempts = ((Long) ruleJSON.get("correctAttempts")).intValue();
    
                // Load questions for this rule
                List<GrammarQuestion> questions = new ArrayList<>();
                JSONArray questionsArray = (JSONArray) ruleJSON.get("questions");
                
                // Integrate your code here to parse each question
                for (Object qObj : questionsArray) {
                    JSONObject questionJSON = (JSONObject) qObj;
                    String text = (String) questionJSON.get("text");
                    String type = (String) questionJSON.get("type");
                    
                    if ("input".equals(type)) {
                        String expectedAnswer = (String) questionJSON.get("expectedAnswer");
                        questions.add(new GrammarQuestion(text, null, -1, type, expectedAnswer));
                    } else {
                        // Handle multiple-choice as before
                        List<String> options = new ArrayList<>();
                        JSONArray optionsArray = (JSONArray) questionJSON.get("options");
                        for (Object option : optionsArray) {
                            options.add((String) option);
                        }
                        int correctAnswer = ((Long) questionJSON.get("correctAnswer")).intValue();
                        questions.add(new GrammarQuestion(text, options, correctAnswer, "multiple-choice", null));
                    }
                }
    
                // Add rule with its questions
                GrammarRule rule = new GrammarRule(ruleText, explanation, examples, attempts, correctAttempts, questions);
                grammarRules.add(rule);
            }
    
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    
        return grammarRules;
    }
    

    public static JSONArray loadGrammarRulesJSON() {
        JSONArray grammarRulesArray = new JSONArray();
        try (FileReader reader = new FileReader(GRAMMAR_RULES_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            grammarRulesArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return grammarRulesArray;
    }

    public static User loadUser(String username) {
        try (FileReader reader = new FileReader("users.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray usersArray = (JSONArray) jsonObject.get("users");

            for (Object obj : usersArray) {
                JSONObject userObj = (JSONObject) obj;
                if (userObj.get("username").equals(username)) {
                    return createUserFromJson(userObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static User createUserFromJson(JSONObject userObj) {
        String id = (String) userObj.get("id");
        String firstName = (String) userObj.get("first_name");
        String lastName = (String) userObj.get("last_name");
        String email = (String) userObj.get("email");
        String username = (String) userObj.get("username");
        String password = (String) userObj.get("password");

        // Extract progress data
        JSONObject progressData = (JSONObject) userObj.get("progress");
        ProgressTracker progressTracker = new ProgressTracker(Map.of(
            "totalRules", ((Long) progressData.get("totalRules")).intValue(),
            "completedRules", ((Long) progressData.get("completedRules")).intValue()
            // Add other progress fields similarly
        ));

        return new User(id, firstName, lastName, email, username, password, progressTracker);
    }

    /*
     * Method to use mock data for testing
     */
    public static void setMockData(List<Word> words) {

        if (words == null) {
            mockData = new ArrayList<>();
        } else {
            mockData = words;

        }
        useMockData = true;
    }

    /*
     * Method to reset mock data
     */
    public static void clearMockData() {
        mockData = null;
        useMockData = false;
    }

    private static JSONArray mockUsers = null; // Variable to hold mock user data

    public static void setMockUsers(JSONArray users) {
        mockUsers = users;
        useMockData = true;
    }

    private static List<Story> mockStories = null;
    public static void setMockStories(List<Story> stories) {
        mockStories = stories;
        useMockData = true;
    }

    public static ArrayList<Story> loadStories() {
        if (useMockData && mockStories != null) {
            return new ArrayList<>(mockStories);
        }

        ArrayList<Story> stories = new ArrayList<>();

         try (FileReader reader = new FileReader(STORY_FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray storiesJSON = (JSONArray) parser.parse(reader);

            for (Object obj : storiesJSON) {
                JSONObject storyJSON = (JSONObject) obj;
                String title = (String) storyJSON.get(STORY_TITLE);
                String text = (String) storyJSON.get(STORY_TEXT);
                
                // Parse word list from JSON
                JSONArray wordsArray = (JSONArray) storyJSON.get(STORY_WORDLIST);
                ArrayList<String> wordList = new ArrayList<>();
                for (Object word : wordsArray) {
                    wordList.add((String) word);
                }

                // Create and add new Story object
                stories.add(new Story(title, text, wordList));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return stories;
    }


}