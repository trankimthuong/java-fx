package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.model.User;

public class MainViewController {
    @FXML
    private Button userManagementButton;

    @FXML
    private Button bookManagementButton;

    @FXML
    private void handleUserManagement() {
        loadScene("../user_management.fxml"); // Đường dẫn tới file FXML quản lý người dùng
    }

    @FXML
    private void handleBookManagement() {
        loadScene("../book_management.fxml"); // Đường dẫn tới file FXML quản lý sách
    }

    @FXML
    public void initialize() {
        userManagementButton.setFont(Font.font("Arial", 14));
        bookManagementButton.setFont(Font.font("Arial", 14));
    }

    private void loadScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) userManagementButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
