package com.email.sys.controllers;

import com.email.sys.Contents;
import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.configurators.ConfigKey;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.entities.Email;
import com.email.sys.services.EmailService;
import com.email.sys.services.SessionService;
import com.email.sys.trackers.ContentManager;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class InboxController implements Initializable {

    private final EmailService emailService;
    private final ConfigStorage configStorage;

    enum InboxFilters{
        ALL("All"),
        STARRED("Starred"),
        SPAM("Spam");

        final String filterName;

        InboxFilters(String filterName) {
            this.filterName = filterName;
        }

        @Override
        public String toString() {
            return filterName;
        }
    }

    private final ContentManager contentManager;
    private final SessionService sessionService;

    String previousFilter;

    @FXML ListView<Email> emails;
    @FXML TextField searchBar;
    @FXML Button searchButton;
    @FXML ComboBox<InboxFilters> filterComboBox;

    @Autowired
    public InboxController(SessionService sessionService, ContentManager contentManager, EmailService emailService, ConfigStorage configStorage) {
        this.sessionService = sessionService;
        this.contentManager = contentManager;
        this.emailService = emailService;
        this.configStorage = configStorage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emails.setCellFactory(new EmailCellFactory(emailService));

        emails.setOnMouseClicked(this::openEmail);
        emails.setItems(emailService.getInboxForUser(sessionService.getUser()));
        searchButton.setOnAction(this::filterInbox);
        filterComboBox.setItems(FXCollections.observableArrayList(InboxFilters.values()));
        filterComboBox.valueProperty().addListener(this::updateInboxCombo);
    }

    private void updateInboxCombo(Observable observable) {
        emails.setItems(switch (filterComboBox.getSelectionModel().getSelectedItem()) {
            case ALL -> FXCollections.observableArrayList(sessionService.getUser().getInboxEmails());
            case SPAM -> emailService.getSpamEmailsForUser(sessionService.getUser());
            case STARRED -> emailService.getStarredMessagesForUser(sessionService.getUser());
        });
    }

    private void openEmail(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            Email email = emails.getSelectionModel().getSelectedItem();
            if(email != null) {
                configStorage.add(ConfigKey.EMAIL, email);

                contentManager.proceedTo(Contents.EMAIL, configStorage);
            }
        }
    }

    void filterInbox(ActionEvent actionEvent) {
        String filter = searchBar.getText();
        if(Objects.equals(previousFilter, filter))
            return;

        emails.setItems(emailService.getFilteredInboxForUser(sessionService.getUser(), filter));
        previousFilter = filter;
    }
}
