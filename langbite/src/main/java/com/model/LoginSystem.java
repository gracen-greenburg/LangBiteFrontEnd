package com.model;

import java.util.Scanner;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LoginSystem {

    public static JSONObject login(Scanner scanner) {
        try {
            // Load user data from DataLoader
            JSONArray users = DataLoader.loadUsers();

            System.out.println("Please enter your username:");
            String usernameInput = scanner.nextLine();

            JSONObject user = findUserByUsername(users, usernameInput);

            if (user != null) {
                System.out.println("Please enter your password:");
                String passwordInput = scanner.nextLine();

                String storedPassword = (String) user.get("password");
                if (storedPassword.equals(passwordInput)) {
                    System.out.println("Welcome " + user.get("first_name") + "! You have successfully logged in.");
                    return user;  // Return the logged-in user's data
                } else {
                    System.out.println("Incorrect username or password. Please try again.");
                }
            } else {
                System.out.println("Incorrect username or password. Please try again.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while accessing user data.");
        }
        return null;  // Return null if login fails
    }

    public static JSONObject signUp(Scanner scanner) {
        try {
            // Load user data from DataLoader
            JSONArray users = DataLoader.loadUsers();

            System.out.println("Please enter your first name:");
            String firstName = scanner.nextLine();

            System.out.println("Please enter your last name:");
            String lastName = scanner.nextLine();

            System.out.println("Please enter your email address:");
            String email = scanner.nextLine();

            String username;
            while (true) {
                System.out.println("Please enter a username:");
                username = scanner.nextLine();

                if (findUserByUsername(users, username) != null) {
                    System.out.println("Username taken. Please enter a different username.");
                } else {
                    break;
                }
            }

            JSONObject newUser = new JSONObject();
            newUser.put("id", UUID.randomUUID().toString());  // Generate a unique UUID for the new user
            newUser.put("first_name", firstName);
            newUser.put("last_name", lastName);
            newUser.put("email", email);
            newUser.put("username", username);

            String password;
            while (true) {
                System.out.println("Please enter a password:");
                password = scanner.nextLine();

                System.out.println("Please reenter the password:");
                String passwordConfirm = scanner.nextLine();

                if (password.equals(passwordConfirm)) {
                    newUser.put("password", password);
                    break;
                } else {
                    System.out.println("Passwords do not match. Please try again.");
                }
            }

            // Add default progress data
            JSONObject progress = new JSONObject();
            progress.put("grammarRulesCompletionPercentage", 0);
            progress.put("connectorWordsCompletionPercentage", 0);
            progress.put("mostCommonWordsCompletionPercentage", 0);
            newUser.put("progress", progress);

            users.add(newUser);

            // Save updated user data using DataWriter
            DataWriter.saveUsers(users);

            System.out.println("Welcome " + firstName + "! You have successfully signed up.");
            return newUser;  // Return the new user's data after sign-up

        } catch (Exception e) {
            System.out.println("An error occurred while accessing user data.");
        }
        return null;  // Return null if sign-up fails
    }

    private static JSONObject findUserByUsername(JSONArray users, String username) {
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.get("username").equals(username)) {
                return user;
            }
        }
        return null;
    }
}
