package com.email.sys.controllers;

import com.email.sys.cell.factories.UsersCellFactory;
import com.email.sys.configurators.ConfigKey;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.entities.Email;
import com.email.sys.entities.ForwardedEmail;
import com.email.sys.entities.User;
import com.email.sys.repositories.EmailRepository;
import com.email.sys.repositories.ForwardedEmailsRepository;
import com.email.sys.repositories.UserRepository;
import com.email.sys.services.EmailService;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import com.email.sys.trackers.ContentManager;
import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ForwardEmailController implements Initializable {
    private final ContentManager contentManager;
    private final EmailService emailService;
    private final UserService userService;
    private final ConfigStorage configStorage;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final ForwardedEmailsRepository forwardedEmailsRepository;
    private final EmailRepository emailRepository;


    @FXML
    private TextField userSearch;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<User> usersList;
    @FXML
    private Button forwardButton;
    @FXML
    private Button backButton;

    public ForwardEmailController(ContentManager contentManager, EmailService emailService, UserService userService, ConfigStorage configStorage, SessionService sessionService, UserRepository userRepository, ForwardedEmailsRepository forwardedEmailsRepository, EmailRepository emailRepository) {
        this.contentManager = contentManager;
        this.emailService = emailService;
        this.userService = userService;
        this.configStorage = configStorage;
        this.sessionService = sessionService;
        this.userRepository = userRepository;
        this.forwardedEmailsRepository = forwardedEmailsRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersList.setCellFactory(new UsersCellFactory(configStorage));

        searchButton.setOnAction(this::searchEmail);
        backButton.setOnAction(evt -> contentManager.goBack());
        usersList.setItems(FXCollections.observableArrayList(userRepository.findAll()));
        forwardButton.setOnAction(this::forwardEmail);
    }

    public void forwardEmail(ActionEvent actionEvent) {
        Email email = configStorage.getIfPresent(ConfigKey.EMAIL);
        User forwarder = sessionService.getUser();
        User forwardTo = usersList.getSelectionModel().getSelectedItem();

        emailService.forward(email, forwarder, forwardTo);
    }

    private void searchEmail(ActionEvent actionEvent) {
        usersList.setItems(userService.searchForEmail(userSearch.getText()));
    }
}
