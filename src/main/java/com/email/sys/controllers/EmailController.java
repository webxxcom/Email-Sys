package com.email.sys.controllers;

import com.email.sys.entities.Email;
import com.email.sys.trackers.ContentManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EmailController implements DataInjectable<Email> {
    private final ContentManager contentManager;
    Email email;

    @FXML Label emailSubject;
    @FXML Label emailSender;
    @FXML TextArea emailBody;
    @FXML Button backButton;
    @FXML Button replyButton;
    @FXML Button forwardButton;

    public EmailController(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Override
    public void inject(Email email) {
        this.email = email;
    }

    @Override
    public void init() {
        emailSubject.setText(email.getHeader());
        emailSender.setText(email.getSender().getEmail());
        emailBody.setText(email.getText());
        backButton.setOnAction(evt -> contentManager.goBack());
    }
}
