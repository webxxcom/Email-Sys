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
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SignUpController implements Initializable {
    private final SceneManager sceneManager;
    private final UserService userService;

    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private Label errorLabel;

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

    public void returnBackToLogin(){
        sceneManager.switchScene(Views.LOG_IN);
    }

    public void tryRegisterUser(){
        Result<User> result = userService.trySignUp(emailField.getText(), passwordField.getText(), confirmPasswordField.getText());
        if(result.hasError()){
            ElementsUtils.showErrorLabel(errorLabel, result.getError());
        } else{
            sceneManager.switchScene(Views.MAIN_PAGE);
        }
    }
}
