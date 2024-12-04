package com.controllers;

import com.model.DataLoader;
import com.model.GrammarRule;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class explanationController {
    @FXML private Label explanationField;

    private GrammarRule grammarRule;
    private Runnable returnCallback;

    public void setGrammarRule(GrammarRule grammarRule) {
        this.grammarRule = grammarRule;
        displayGrammarRule();
    }

    public void setReturnCallback(Runnable returnCallback) {
        this.returnCallback = returnCallback;
    }

    private void displayGrammarRule() {
        if (grammarRule != null) {
            explanationField.setText(grammarRule.getExplanation());
        } else {
            explanationField.setText("No explanation available.");
        }
    }

    @FXML
    private void handleNextButton() {
        if (returnCallback != null) {
            returnCallback.run();
        }
    }


}
