package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LanguageSystemApplicationTest {

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

        users.add(user);

        // Mock DataLoader to return this mock user data
        DataLoader.setMockUsers(users);
    }

    @Test
    public void testRunLoginSystem_SuccessfulLogin() throws Exception {
        // Simulate user input for logging in with correct credentials
        String simulatedInput = "1\njohndoe\npassword123\n";
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        Method runLoginSystem = LanguageSystemApplication.class.getDeclaredMethod("runLoginSystem", Scanner.class);
        runLoginSystem.setAccessible(true);
        JSONObject loggedInUser = (JSONObject) runLoginSystem.invoke(null, scanner);

        assertNotNull("User should be logged in successfully", loggedInUser);
        assertEquals("John", loggedInUser.get("first_name"));
    }

    @Test
    public void testRunLoginSystem_LoginFailure() throws Exception {
        // Simulate user input for incorrect login
        String simulatedInput = "1\njohndoe\nwrongpassword\n";
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        Method runLoginSystem = LanguageSystemApplication.class.getDeclaredMethod("runLoginSystem", Scanner.class);
        runLoginSystem.setAccessible(true);
        JSONObject loggedInUser = (JSONObject) runLoginSystem.invoke(null, scanner);

        assertNull("User should not be logged in with incorrect password", loggedInUser);
    }

    @Test
    public void testRunLoginSystem_SignUpSuccess() throws Exception {
        // Simulate user input for signing up with new credentials
        String simulatedInput = "2\nJane\nDoe\njane.doe@example.com\njanedoe\npassword123\npassword123\n";
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        Method runLoginSystem = LanguageSystemApplication.class.getDeclaredMethod("runLoginSystem", Scanner.class);
        runLoginSystem.setAccessible(true);
        JSONObject newUser = (JSONObject) runLoginSystem.invoke(null, scanner);

        assertNotNull("New user should be signed up successfully", newUser);
        assertEquals("Jane", newUser.get("first_name"));
    }
}
