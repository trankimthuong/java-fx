package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.dao.UserDao;
import sample.model.User;

public class UserController {
    @FXML
    private ListView<User> userListView;
    @FXML
    private TextField nameField, addressField, numberPhoneField, emailField;
    @FXML
    private Button createButton, editButton, deleteButton, backButton;
    @FXML
    private void handleBack() {
        loadScene("main_view.fxml");
    }

    private UserDao userDAO = new UserDao();
    private ObservableList<User> userList;

    @FXML
    public void initialize() {
        createButton.setFont(Font.font("Arial", 14));
        backButton.setFont(Font.font("Arial", 14));
        editButton.setFont(Font.font("Arial", 14));
        deleteButton.setFont(Font.font("Arial", 14));
        nameField.setFont(Font.font("Arial", 14));
        addressField.setFont(Font.font("Arial", 14));
        numberPhoneField.setFont(Font.font("Arial", 14));
        emailField.setFont(Font.font("Arial", 14));
        userListView.setCellFactory(lv -> new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.setFont(Font.font("Arial", 14));
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getName()); // Áp dụng font
                }
            }
        });


        loadUsers();
        userListView.setOnMouseClicked(event -> {
            User selectedUser = userListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                fillFields(selectedUser);
            }
        });
    }

    private void loadUsers() {
        userList = FXCollections.observableArrayList(userDAO.getAllUsers());
        userListView.setItems(userList);
    }

    private void fillFields(User user) {
        nameField.setText(user.getName());
        addressField.setText(user.getAddress());
        numberPhoneField.setText(user.getNumberPhone());
        emailField.setText(user.getEmail());
    }

    @FXML
    public void createUser() {
        User newUser = new User(0, nameField.getText(), addressField.getText(), numberPhoneField.getText(), emailField.getText());
        userDAO.createUser(newUser);
        loadUsers();
    }

    @FXML
    public void editUser() {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setName(nameField.getText());
            selectedUser.setAddress(addressField.getText());
            selectedUser.setNumberPhone(numberPhoneField.getText());
            selectedUser.setEmail(emailField.getText());
            userDAO.updateUser(selectedUser);
            loadUsers();
        }
    }

    @FXML
    public void deleteUser() {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userDAO.deleteUser(selectedUser.getId());
            loadUsers();
        }
    }

    private void loadScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../" + fxml));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
