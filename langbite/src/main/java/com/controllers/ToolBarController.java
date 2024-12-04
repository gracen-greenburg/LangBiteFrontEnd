package your.package;

import javafx.fxml.FXML;

public class ToolBarController {

    @FXML
    private void navigateToHome() {
        navigate("HomePage.fxml");
    }

    @FXML
    private void navigateToProfile() {
        navigate("ProfilePage.fxml");
    }

    private void navigate(String fxmlFile) {
        try {
            javafx.scene.Parent page = javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlFile));
            javafx.stage.Stage stage = (javafx.stage.Stage) javafx.scene.Scene.getWindows().get(0);
            stage.getScene().setRoot(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
