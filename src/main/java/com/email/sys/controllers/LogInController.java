package com.email.sys.controllers;

import com.email.sys.*;
import com.email.sys.entities.User;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    @Autowired
    public LogInController(UserService userService, SceneManager sceneManager, SessionService sessionService) {
        this.userService = userService;
        this.sceneManager = sceneManager;
        this.sessionService = sessionService;
    }

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button navigateToSignUpButton;
    @FXML private Label errorLabel;

    private boolean validateCredentials(String email, String password){
        if(email.isEmpty()){
            ElementsUtils.showErrorLabel(errorLabel, "Please fill the email");
            return false;
        }
        if(password.isEmpty()){
            ElementsUtils.showErrorLabel(errorLabel, "Please fill the password");
            return false;
        }
        ElementsUtils.hideErrorLabel(errorLabel);
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(evt -> login());
        navigateToSignUpButton.setOnAction(evt -> navigateToSignUp());
    }

    public void login(){
        String email = emailField.getText();
        String password = passwordField.getText();
        if(!validateCredentials(email, password)){
            return;
        }

        Result<User> res = userService.tryLogIn(email, password);
        if(res.hasError()) {
            ElementsUtils.showErrorLabel(errorLabel, res.getError());
        }else{
            sessionService.setUser(res.getData());
            sceneManager.switchScene(Views.MAIN_PAGE);
        }
    }

    public void navigateToSignUp(){
        sceneManager.switchScene(Views.SIGN_UP);
    }
}
