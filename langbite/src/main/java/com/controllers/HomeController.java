package com.controllers;

import java.io.IOException;
import com.language.App;
import javafx.fxml.FXML;

public class HomeController  {

    @FXML
    private void switchToSignUp() throws IOException {
        App.setRoot("signUpScreen");
    }

    @FXML
    private void username() throws IOException {

    }

    @FXML
    private void password() throws IOException {
        
    }
}

