package com.email.sys.controllers;

import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendController {
    SessionService sessionService;
    UserService userService;

    @FXML private TextField recipientGmail;
    @FXML private TextArea emailText;
    @FXML private Button sendButton;

    @Autowired
    public SendController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public void sendEmail(){
        userService.sendEmail(sessionService.getUser(), recipientGmail.getText(), emailText.getText());
    }

}
