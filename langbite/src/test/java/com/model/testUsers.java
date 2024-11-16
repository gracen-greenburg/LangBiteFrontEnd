package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class testUsers {
    private Users users;

    @Before
    public void setUp() {
        users = Users.getInstance();
        users.getUsers().clear();  // Clear list before each test
    }

    // Tests adding a user
    @Test
    public void testAddUser() {
        users.addUser("Ricky", "Ratatata", "rickrat", "ricktherat@rat.com", "iloveratssomuchxoxos2024");
        assertEquals("Expected 1 user in list after adding", 1, users.getUsers().size());
        assertTrue("User should be present after adding", users.hasUser("rickrat"));
    }

    // Tests adding a duplicate user
    @Test
    public void testAddDuplicateUser() {
        users.addUser("Unique", "Gal", "TheOneAndOnly", "soQuirky@uhhuh.com", "password987");
        users.addUser("Unique", "Gal", "TheOneAndOnly", "soQuirky@uhhuh.com", "password987"); // Duplicate
        assertEquals("Expected only 1 unique user in list", 1, users.getUsers().size());
    }

    // Tests if user can be fetched if they exist
    @Test
    public void testGetUserExists() {
        users.addUser("George", "Washington", "cherryTreeH8ter", "thePrez@example.com", "woodent33th");
        User user = users.getUser("cherryTreeH8ter");
        assertNotNull("Expected user to be found", user);
        assertEquals("Expected username to match", "cherryTreeH8ter", user.getUsername());
    }

    // Tests if user can be fetched if they don't
    @Test
    public void testGetUserDoesNotExist() {
        User user = users.getUser("lolnothing");
        assertNull("Expected null for non-existing user", user);
    }

    // Tests if the updated module updates with new user
    @Test
    public void testUpdateUserModuleExists() {
        users.addUser("John", "Pokemon", "inventorofpokemon", "eevee@mon.com", "squirtle23");
        users.updateUserModule("inventorofpokemon", 3);
        User user = users.getUser("inventorofpokemon");
        assertEquals("Expected module to be updated to 3", 3, user.getCurrentModule());
    }

    // Tests if a user can be added with an empty
    @Test
    public void testAddUserWithEmptyFields() {
        users.addUser("", "Birds", "angryBirdL0v3r", "redandyellow@example.com", "thosegreenpigs");
        assertFalse("User with empty first name should not be added", users.hasUser("jsmith"));

        users.addUser("Angry", "", "angryBirdL0v3r", "redandyellow@example.com", "thosegreenpigs");
        assertFalse("User with empty last name should not be added", users.hasUser("jsmith"));

        users.addUser("Angry", "Birds", "", "redandyellow@example.com", "thosegreenpigs");
        assertFalse("User with empty username should not be added", users.hasUser(""));

        users.addUser("Angry", "Birds", "angryBirdL0v3r", "", "thosegreenpigs");
        assertFalse("User with empty email should not be added", users.hasUser("jsmith"));

        users.addUser("Angry", "Birds", "angryBirdL0v3r", "redandyellow@example.com", "");
        assertFalse("User with empty password should not be added", users.hasUser("jsmith"));
    }

    // Tests if username is case sensitive
    @Test
    public void testCaseSensitivityInUsername() {
        users.addUser("Improv", "Iser", "Overreactors", "overreactorsImprov@example.com", "yesAnd2024");
        assertFalse("Username check should be case-sensitive", users.hasUser("SAMADAMS"));
    }

}
