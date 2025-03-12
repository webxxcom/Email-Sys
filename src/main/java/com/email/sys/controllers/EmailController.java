package com.email.sys.controllers;

import com.email.sys.Contents;
import com.email.sys.configurators.ConfigKey;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.entities.Email;
import com.email.sys.trackers.ContentManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EmailController implements DataInjectable {
    private final ContentManager contentManager;
    @FXML
    Label emailSubject;
    @FXML
    Label emailSender;
    @FXML
    TextArea emailBody;
    @FXML
    Button backButton;
    @FXML
    Button replyButton;
    @FXML
    Button forwardButton;
    private Email email;

    public EmailController(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Override
    public void inject(ConfigStorage config) {
        this.email = config.getIfPresent(ConfigKey.EMAIL);
        this.init();
    }

    @Override
    public void init() {
        emailSubject.setText(email.getEmailContent().getHeader());
        emailSender.setText(email.getSender().getEmail());
        emailBody.setText(email.getEmailContent().getText());
        backButton.setOnAction(evt -> contentManager.goBack());
        forwardButton.setOnAction(this::showForward);
    }

    private void showForward(ActionEvent actionEvent) {
        contentManager.proceedTo(Contents.FORWARD);
    }
}
