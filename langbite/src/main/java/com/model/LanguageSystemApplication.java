package com.model;

import java.util.Scanner;
import org.json.simple.JSONObject;

public class LanguageSystemApplication {

    private static JSONObject currentUser;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Run LoginSystem
        currentUser = runLoginSystem(scanner);

        if (currentUser != null) {
            System.out.println("Welcome to the Language System, " + currentUser.get("first_name") + "!");
            runLanguageList(scanner);
        }
    }

    private static JSONObject runLoginSystem(Scanner scanner) {
        System.out.println("Welcome to the Language System. Please log in or sign up.");

        while (true) {
            System.out.println("Would you like to 1. log in or 2. sign up?");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                JSONObject user = LoginSystem.login(scanner);
                if (user != null) {
                    return user;
                }
            } else if (choice.equals("2")) {
                JSONObject user = LoginSystem.signUp(scanner);
                if (user != null) {
                    return user;
                }
            } else {
                System.out.println("Input not recognized. Please either enter \"1\" for log in or \"2\" for sign up.");
            }
        }
    }

    private static void runLanguageList(Scanner scanner) {
        LanguageList languageList = new LanguageList(currentUser);
        languageList.displayOptions(scanner);
    }
}
