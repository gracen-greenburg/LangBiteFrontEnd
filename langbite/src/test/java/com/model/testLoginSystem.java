package com.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;
import java.util.UUID;

public class testLoginSystem {

    private JSONArray users;

    @Before
    public void setUp() {
        // Initialize a mock user list
        users = new JSONArray();

        // Create a sample user with valid login credentials
        JSONObject user = new JSONObject();
        user.put("id", UUID.randomUUID().toString());
        user.put("first_name", "John");
        user.put("last_name", "Doe");
        user.put("email", "john.doe@example.com");
        user.put("username", "johndoe");
        user.put("password", "password123");

        // Add progress data
        JSONObject progress = new JSONObject();
        progress.put("grammarRulesCompletionPercentage", 50);
        progress.put("connectorWordsCompletionPercentage", 60);
        progress.put("mostCommonWordsCompletionPercentage", 70);
        user.put("progress", progress);

        users.add(user);

        // Mock DataLoader to return this mock user data
        DataLoader.setMockUsers(users);
    }

    // Test the login method with correct credentials
    @Test
    public void testLogin_Successful() {
        Scanner scanner = new Scanner("johndoe\npassword123\n");
        JSONObject loggedInUser = LoginSystem.login(scanner);

        assertNotNull("User should be logged in successfully", loggedInUser);
        assertEquals("John", loggedInUser.get("first_name"));
        assertEquals("Doe", loggedInUser.get("last_name"));
    }

    // Test the login method with incorrect credentials
    @Test
    public void testLogin_Failure() {
        Scanner scanner = new Scanner("johndoe\nwrongpassword\n");
        JSONObject loggedInUser = LoginSystem.login(scanner);

        assertNull("User should not be logged in with incorrect password", loggedInUser);
    }

    // Test the signUp method with valid information and unique username
    @Test
    public void testSignUp_Successful() {
        Scanner scanner = new Scanner("Jane\nDoe\njane.doe@example.com\njanedoe\npassword123\npassword123\n");
        JSONObject newUser = LoginSystem.signUp(scanner);

        assertNotNull("New user should be signed up successfully", newUser);
        assertEquals("Jane", newUser.get("first_name"));
        assertEquals("Doe", newUser.get("last_name"));
        assertEquals("janedoe", newUser.get("username"));
    }

    // Test the signUp method with a duplicate username
    @Test
    public void testSignUp_UsernameTaken() {
        Scanner scanner = new Scanner("Jane\nDoe\njane.doe@example.com\njohndoe\npassword123\npassword123\n");
        JSONObject newUser = LoginSystem.signUp(scanner);

        assertNull("User should not be signed up with a duplicate username", newUser);
    }

    // Test the signUp method with mismatched passwords
    @Test
    public void testSignUp_PasswordMismatch() {
        Scanner scanner = new Scanner("Jane\nDoe\njane.doe@example.com\njanedoe\npassword123\nwrongpassword\npassword123\npassword123\n");
        JSONObject newUser = LoginSystem.signUp(scanner);

        assertNotNull("User should eventually sign up after correcting password mismatch", newUser);
    }
}
