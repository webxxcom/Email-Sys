package com.email.sys.controllers;

import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.entities.Email;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class InboxController implements Initializable {

    UserService userService;
    SessionService sessionService;

    @FXML public ListView<Email> contentInnerPane;
    @FXML public TextField searchBar;
    @FXML public Button searchButton;

    @Autowired
    public InboxController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contentInnerPane.setCellFactory(new EmailCellFactory());
        contentInnerPane.setItems(userService.getInbox(sessionService.getUser().getId()));
    }
}
