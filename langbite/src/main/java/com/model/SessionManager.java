package com.model;

import org.json.simple.JSONObject;

public class SessionManager {
    private static JSONObject currentUser;

    // Set the currently logged-in user
    public static void setCurrentUser(JSONObject user) {
        currentUser = user;
    }

    // Get the currently logged-in user
    public static JSONObject getCurrentUser() {
        return currentUser;
    }

    // Clear the session (for logout)
    public static void clearSession() {
        currentUser = null;
    }
}