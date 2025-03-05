package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.services.EmailService;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class SendController implements Initializable, Resettable {
    private final SessionService sessionService;
    private final UserService userService;
    private final EmailService emailService;

    @FXML private Label successLabel;
    @FXML private Label errorLabel;
    @FXML private TextField recipientEmailField;
    @FXML private TextField emailHeaderField;
    @FXML private TextArea emailTextArea;
    @FXML private Button sendButton;

    @Autowired
    public SendController(SessionService sessionService, UserService userService, EmailService emailService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.emailService = emailService;
    }

    public void sendEmail(ActionEvent actionEvent){
        String recipientEmailText = recipientEmailField.getText();
        String emailText = emailTextArea.getText();

        Result<?> res;
        if(recipientEmailText.isBlank()){
            res = Result.ofError("Please fill the email field");
        } else if(emailText.isBlank()){
            res = Result.ofError("The empty letter cannot be sent");
        } else {
            res = emailService.sendEmail(emailHeaderField.getText(), emailText, sessionService.getUser(), recipientEmailText);
        }
        ElementsUtils.showCorrespondingLabel(res, successLabel, errorLabel, this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendButton.setOnAction(this::sendEmail);
    }

    @Override
    public void reset() {
        recipientEmailField.setText("");
        emailHeaderField.setText("");
        emailTextArea.setText("");
    }
}
