package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.SceneManager;
import com.email.sys.Views;
import com.email.sys.entities.User;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class LogInController implements Initializable {

    private final UserService userService;
    private final SceneManager sceneManager;
    private final SessionService sessionService;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button navigateToSignUpButton;
    @FXML private Label errorLabel;

    @Autowired
    public LogInController(UserService userService, SceneManager sceneManager, SessionService sessionService) {
        this.userService = userService;
        this.sceneManager = sceneManager;
        this.sessionService = sessionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(evt -> login());
        navigateToSignUpButton.setOnAction(evt -> navigateToSignUp());
        passwordField.setOnKeyPressed(evt -> {
            if (evt.getCode().equals(KeyCode.ENTER)) {
                login();
            }
        });
        emailField.setOnKeyPressed(evt -> {
            if (evt.getCode().equals(KeyCode.ENTER)) {
                passwordField.requestFocus();
            }
        });
    }

    public void login() {
        Result<User> res =
                userService.tryLogIn(emailField.getText(), passwordField.getText());
        if (res.hasError()) {
            ElementsUtils.showLabel(errorLabel, res.message());
        } else {
            sessionService.setUser(res.data());
            sceneManager.goTo(Views.MAIN_PAGE);
        }
    }

    public void navigateToSignUp() {
        sceneManager.goTo(Views.SIGN_UP);
    }
}
