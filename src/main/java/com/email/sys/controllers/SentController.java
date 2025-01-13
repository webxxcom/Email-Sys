package com.email.sys.controllers;

import com.email.sys.SpringFXMLLoader;
import com.email.sys.entities.Email;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SentController implements Initializable {

    private final SessionService sessionService;
    UserService userService;
    SpringFXMLLoader loader;

    @FXML private TextField searchBar;
    @FXML private Button searchButton;
    @FXML private ListView<Email> sentEmails;

    public SentController(SessionService sessionService, UserService userService, SpringFXMLLoader loader) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.loader = loader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sentEmails.setItems(userService.getSent(sessionService.getUser().getId()));
    }
}
