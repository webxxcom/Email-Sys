package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.SceneManager;
import com.email.sys.Views;
import com.email.sys.entities.User;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SignUpController implements Initializable, Resettable {
    private final SceneManager sceneManager;
    private final UserService userService;

    @Value("${email.postfix}")
    private String postfix;

    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label errorLabel;
    @FXML private Label successLabel;
    @FXML private Button returnButton;
    @FXML private Button submitButton;

    @Autowired
    public SignUpController(UserService userService, SceneManager sceneManager) {
        this.userService = userService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnButton.setOnAction(evt -> returnBackToLogin());
        submitButton.setOnAction(evt -> tryRegisterUser());
    }

    public void returnBackToLogin() {
        sceneManager.switchScene(Views.LOG_IN);
    }

    public void tryRegisterUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!username.matches("^[a-zA-Z0-9._%+-]+$")) {
            ElementsUtils.showLabel(errorLabel, "Username contains invalid characters");
        } else if (password.isBlank() || confirmPassword.isBlank()) {
            ElementsUtils.showLabel(errorLabel, "Please fill the password fields");
        } else if (!password.equals(confirmPassword)) {
            ElementsUtils.showLabel(errorLabel, "Passwords should match");
        } else {
            Result<User> result = userService.trySignUp(
                    username + postfix,
                    password
            );
            ElementsUtils.showCorrespondingLabel(result, successLabel, errorLabel, this);
        }
    }

    @Override
    public void reset() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
