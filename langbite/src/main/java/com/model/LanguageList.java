package com.model;

import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LanguageList {
    private JSONObject currentUser;

    public LanguageList(JSONObject currentUser) {
        this.currentUser = currentUser;
    }

    public void displayOptions(Scanner scanner) {
        System.out.println("Welcome to your Language List.");

        while (true) {
            System.out.println("Options: 1. Add Language  2. Remove Language  3. Open Language  4. Logout");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addLanguage();
                    break;
                case "2":
                    removeLanguage(scanner);
                    break;
                case "3":
                    openLanguage(scanner);
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    protected LanguageModule createLanguageModule() {
        return new LanguageModule(currentUser);
    }

    private void addLanguage() {
        JSONArray languages = (JSONArray) currentUser.get("languages");
        if (languages == null) {
            languages = new JSONArray();
            currentUser.put("languages", languages);
        }

        if (!languages.contains("French")) {
            languages.add("French");
            DataWriter.saveUser(currentUser);  // Save updated user data
            System.out.println("French language added to your list!");
        } else {
            System.out.println("French is already in your list.");
        }
    }

    private void removeLanguage(Scanner scanner) {
        JSONArray languages = (JSONArray) currentUser.get("languages");

        if (languages == null || languages.isEmpty()) {
            System.out.println("No languages to remove.");
            return;
        }

        System.out.println("Your languages: " + languages);
        System.out.println("Enter the language you want to remove:");
        String language = scanner.nextLine();

        if (languages.remove(language)) {
            DataWriter.saveUser(currentUser);  // Save updated user data
            System.out.println(language + " has been removed from your list.");
        } else {
            System.out.println("Language not found in your list.");
        }
    }

    private void openLanguage(Scanner scanner) {
        JSONArray languages = (JSONArray) currentUser.get("languages");

        if (languages == null || languages.isEmpty()) {
            System.out.println("You have no languages added. Add a language first.");
            return;
        }

        System.out.println("Available languages: " + languages);
        System.out.println("Enter the language you want to open:");
        String language = scanner.nextLine();

        if (languages.contains(language)) {
            if (language.equalsIgnoreCase("French") || language.equalsIgnoreCase("french")) {
                LanguageModule languageModule = new LanguageModule(currentUser);
                languageModule.startModule(scanner);
            } else {
                System.out.println("This language is not yet supported.");
            }
        } else {
            System.out.println("Language not found in your list.");
        }
    }
}
