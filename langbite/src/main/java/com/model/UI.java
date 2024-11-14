package com.model;
/*
 * Class UI
 *  
 * @author LangBite Team
 */
public interface UI {
    
    /**
     * Logs the user into the system.
     * 
     * @return true if the login is successful, false otherwise.
     */
    boolean login();
    
    /**
     * Logs the current user out of the system.
     */
    void logout();
    
    /**
     * Registers a new user in the system.
     */
    void register();
    
    /**
     * Displays a list of all users in the system.
     */
    void showUsers();

        /**
     * Starts the application, allowing the user to interact with it.
     */
    void startApplication();

}



