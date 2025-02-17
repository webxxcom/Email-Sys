package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SendController implements Initializable, Resettable {
    SessionService sessionService;
    UserService userService;

    @FXML private Label successLabel;
    @FXML private Label errorLabel;
    @FXML private TextField recipientGmail;
    @FXML private TextField headerField;
    @FXML private TextArea emailText;
    @FXML private Button sendButton;

    @Autowired
    public SendController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public void sendEmail(){
        Result<Email> res = userService.sendEmail(headerField.getText(), emailText.getText(), sessionService.getUser(), recipientGmail.getText());
        ElementsUtils.showCorrespondingLabel(res, successLabel, errorLabel, this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendButton.setOnAction(evt -> sendEmail());
    }

    @Override
    public void reset() {
        recipientGmail.setText("");
        headerField.setText("");
        emailText.setText("");
    }
}
