package com.email.sys.controllers;

import com.email.sys.services.EmailService;
import com.email.sys.services.UserService;
import com.email.sys.trackers.ContentManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ForwardEmailController implements Initializable {
    private final ContentManager contentManager;
    private final EmailService emailService;
    private final UserService userService;

    @FXML
    private TextField userSearch;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<String> usersList;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;

    public ForwardEmailController(ContentManager contentManager, EmailService emailService, UserService userService) {
        this.contentManager = contentManager;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchButton.setOnAction(this::searchEmail);
        backButton.setOnAction(evt -> contentManager.goBack());
        usersList.setItems(emailService.getAvailableEmails());
    }

    private void searchEmail(ActionEvent actionEvent) {
        ObservableList<String> observableList = userService.searchForEmail(userSearch.getText());
        usersList.setItems(observableList);
    }
}
