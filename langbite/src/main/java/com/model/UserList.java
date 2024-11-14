package com.narriation;
import java.util.ArrayList;
import java.util.UUID;
/*
 * UserList
 * 
 * @author LangBite Team
 */
public class UserList {
    /*
     * UserList manages users and makes sure that the list is maintained
     * 
     * @param userListInstance UserList ensures that there is only one instance of UserList
     * @param users ArrayList<User> a list of the users in the system
     */
    private static UserList userListInstance;
    private ArrayList<User> users;

    /*
     * Class UserList instantiates UserList and users
     */
    private UserList() {
        users = new ArrayList<>();
    }

    /*
     * Class getInstance makes sure that this is a
     * Singleton instance of UserList
     */
    public static UserList getInstance() {
        if (userListInstance == null) {
            userListInstance = new UserList();
        }
        return userListInstance;
    }

    /*
     * Class addUser adds a user to the list
     */
    public void addUser(User user) {
        users.add(user);
    }

    /*
     * Class removeUser removes a user from the list by their UUID
     */
    public void removeUser(UUID userId) {
        users.removeIf(user -> user.getUsername().equals(userId));
    }

    /*
     * Class updateUser updates infromation of a user that is already in the list
     */
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser); // replace old user with updated user
                return;
            }
        }
    }

    /*
     * Class getUser retrieves a user from the list by their UUID
     */
    public User getUser(UUID userId) {
        for (User user : users) {
            if (user.getUsername().equals(userId)) {
                return user;
            }
        }
        return null; 
    }

    /*
     * C;ass getAllUsers gets the list of all users
     */
    public ArrayList<User> getAllUsers() {
        return users;
    }
}