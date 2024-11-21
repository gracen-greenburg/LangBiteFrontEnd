package com.controllers;

import java.io.IOException;
import com.language.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class SignUpController {
    @FXML private TextField txt_firstName;
    @FXML private TextField txt_lastName;
    @FXML private TextField txt_email;
    @FXML private TextField txt_password;

    private void switchToLanguageSelect() throws IOException {
        String firstName  = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();
    }
}
