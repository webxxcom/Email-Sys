package com.email.sys.controllers;

import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController {

    private UserService userService = new UserService();

    @FXML TextField signupUsernameForm;
    @FXML Button returnButton;

    @FXML
    public void returnBackToLogin(){
        //JavaFxApplication.switchScene(Views.LOG_IN);
    }
}
