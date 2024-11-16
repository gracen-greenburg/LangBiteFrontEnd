package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertTrue;
import java.io.StringReader;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class DataWriterTest {
  

        @Test
    public void testSaveUsers() {
        // Simulate user input for the signUp process
        String simulatedInput = "Test\nUser\ntestuser@example.com\ntestuser\npassword123\npassword123\n";
        Scanner scanner = new Scanner(new StringReader(simulatedInput));

        // Call signUp and save the user
        LoginSystem.signUp(scanner);

        // Load the users from User.json to check if the new user was saved
        JSONArray users = DataLoader.loadUsers();
        boolean found = false;

        // Iterate over the JSONArray to find the user
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if ("Test".equalsIgnoreCase((String) user.get("first_name"))) {
                found = true;
                break;
            }
        }

        // Assert that the user was found in the JSON data
        assertTrue("The user 'Test' should be found in the loaded data", found);
    }
    
    
}

