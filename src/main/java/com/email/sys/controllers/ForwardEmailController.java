package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.cell.factories.UsersCellFactory;
import com.email.sys.configurators.ConfigKey;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import com.email.sys.services.EmailService;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import com.email.sys.trackers.ContentManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private final ConfigStorage configStorage;
    private final SessionService sessionService;

    @FXML private TextField searchBar;
    @FXML private Button searchButton;
    @FXML private ListView<User> usersList;
    @FXML private Button forwardButton;
    @FXML private Button cancelButton;
    @FXML private Label successLabel;
    @FXML private Label errorLabel;

    public ForwardEmailController(ContentManager contentManager, EmailService emailService, UserService userService, ConfigStorage configStorage, SessionService sessionService) {
        this.contentManager = contentManager;
        this.emailService = emailService;
        this.userService = userService;
        this.configStorage = configStorage;
        this.sessionService = sessionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersList.setCellFactory(new UsersCellFactory(configStorage));

        cancelButton.setOnAction(evt -> contentManager.goBack());
        forwardButton.setOnAction(this::forwardEmail);
        initUserSearch();
        initUsersList();
    }

    private void initUserSearch(){
        searchButton.setOnAction(evt ->
                usersList.setItems(userService.searchForEmail(searchBar.getText()))
        );

        /* Reduce number of queries by adding debouncing search */
        ElementsUtils.addDebouncingActionEventForProperty(
                searchBar.textProperty(),
                400,
                event ->
                        /* Search is cached hence it's not expensive */
                        usersList.setItems(userService.performUserSearch(
                                searchBar.getText(),
                                sessionService.getUser())
                        )
        );
    }

    private void initUsersList(){
        /* Set user's list values */
        usersList.setItems(userService.getUsersListFor(sessionService.getUser()));
    }

    public void forwardEmail(ActionEvent actionEvent) {
        Email email = configStorage.getIfPresent(ConfigKey.EMAIL);
        User forwarder = sessionService.getUser();
        User forwardTo = usersList.getSelectionModel().getSelectedItem();

        Result<Email> forward = emailService.forward(email, forwarder, forwardTo);
        ElementsUtils.showCorrespondingLabel(forward, successLabel, errorLabel);
    }
}
