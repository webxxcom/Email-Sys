package com.email.sys.controllers;

import com.email.sys.Contents;
import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.configurators.ConfigKey;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.entities.Email;
import com.email.sys.repositories.EmailRepository;
import com.email.sys.services.EmailService;
import com.email.sys.services.SessionService;
import com.email.sys.trackers.ContentManager;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class InboxController implements Initializable {

    private final EmailService emailService;
    private final ConfigStorage configStorage;
    private final ContentManager contentManager;
    private final SessionService sessionService;
    private final EmailRepository emailRepository;

    @FXML ListView<Email> emails;
    @FXML TextField searchBar;
    @FXML ComboBox<InboxFilters> filterComboBox;

    private FilteredList<Email> filteredEmails;
    @Autowired
    public InboxController(SessionService sessionService, ContentManager contentManager, EmailService emailService, ConfigStorage configStorage, EmailRepository emailRepository) {
        this.sessionService = sessionService;
        this.contentManager = contentManager;
        this.emailService = emailService;
        this.configStorage = configStorage;
        this.emailRepository = emailRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emails.setCellFactory(new EmailCellFactory(emailService));

        emails.setOnMouseClicked(this::openEmail);
        filteredEmails = new FilteredList<>(FXCollections.observableArrayList(emailService.getInboxForUser(sessionService.getUser())));
        emails.setItems(filteredEmails);
        searchBar.textProperty().addListener(this::filterInbox);
        filterComboBox.setItems(FXCollections.observableArrayList(InboxFilters.values()));
        filterComboBox.valueProperty().addListener(this::updateInboxCombo);
    }

    private void filterInbox(Observable observable) {
        filteredEmails.setPredicate(em -> em
                .getEmailContent().getText()
                .toLowerCase()
                .contains(searchBar.getText().toLowerCase())
        );
    }

    private void updateInboxCombo(Observable observable) {
        switch (filterComboBox.getSelectionModel().getSelectedItem()) {
            case ALL -> filteredEmails.setPredicate(em -> true);
            case SPAM -> filteredEmails.setPredicate(em -> false);
            case STARRED -> filteredEmails.setPredicate(Email::isStarred);
        }
    }

    private void openEmail(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Email email = emails.getSelectionModel().getSelectedItem();
            if (email != null) {
                configStorage.add(ConfigKey.EMAIL, email);
                contentManager.proceedTo(Contents.EMAIL);
            }
        }
    }

    enum InboxFilters {
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
}
