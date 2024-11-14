package com.controllers;

import java.io.IOException;
import com.language.App;
import javafx.fxml.FXML;
import com.narriation.*;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        //App.setRoot("secondary");
        Narriator.playSound("Bienvenue a Langbite");
    }
}
