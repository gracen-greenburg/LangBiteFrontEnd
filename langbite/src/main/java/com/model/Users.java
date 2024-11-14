package com.narriation;
import java.util.ArrayList;
/*
 * Users
 * 
 * @author LangBite Team
 */
public class Users {
    /*
     * Users helps manage new users, manages checking for new users, and
     * can retrieve the list of users
     */
    private static Users users = null;
    private static ArrayList<User> userList = new ArrayList<User>();

    /*
     * Class Users instantiates userList
     */
    private Users() {
        userList = DataLoader.loadUsers();
    } 

    /*
     * Class getInstance gets the Singleton instance of Users
     */
    public static Users getInstance() {
        if(users == null) {
            users = new Users();
        }
        return users;
    }

    public User getUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Return null if user not found
    }


    public ArrayList<User> getUsers() {
        return userList;
    }

    /*
     * Class hasUser checks if a user with the inputted username exists
     */
    public boolean hasUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true; // Username already exists
            }
        }
        return false; // Username not found
    }
    
    /*
     * Class addUser adds a user to the list as long as they are not
     * already on the list with their specified information
     * 
     * @param firstName String the user's first name
     * @param lastName String the user's last name
     * @param userName String the user's username
     * @param password String the user's password
     */
    public void addUser(String firstName, String lastName, String username, String email, String password) {
        if (hasUser(username)) {
            System.out.println("User already exists with username: " + username);
            return;
        } // Username already exists
        else {
        ProgressTracker progressTracker = new ProgressTracker(0,0,0);
        userList.add(new User(firstName, lastName, username, email, password,0, progressTracker));
        DataWriter.saveUsers(); // Username added
        }
    }

    // Update an existing user's current module
    public void updateUserModule(String username, int newModule) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                user.setCurrentModule(newModule);  // Update current module
                DataWriter.saveUsers();  // Save the updated user information
                return;
            }
        }
        System.out.println("User not found: " + username);
    }

    
}