package com.email.sys.controllers;

import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.entities.Email;
import com.email.sys.services.EmailService;
import com.email.sys.services.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class SentController implements Initializable {

    private final SessionService sessionService;
    private final EmailService emailService;

    @FXML private TextField searchBar;
    @FXML private Button searchButton;
    @FXML private ListView<Email> sentEmails;

    public SentController(SessionService sessionService, EmailService emailService) {
        this.sessionService = sessionService;
        this.emailService = emailService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sentEmails.setCellFactory(new EmailCellFactory(emailService));
        sentEmails.setItems(emailService.getSentForUser(sessionService.getUser()));
    }
}
