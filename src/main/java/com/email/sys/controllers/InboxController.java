package com.email.sys.controllers;

import com.email.sys.Contents;
import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
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
    private final UserService userService;
    private final SessionService sessionService;

    String previousFilter;

    @FXML ListView<Email> emails;
    @FXML TextField searchBar;
    @FXML Button searchButton;
    @FXML ComboBox<InboxFilters> filterComboBox;

    @Autowired
    public InboxController(UserService userService, SessionService sessionService, ContentManager contentManager) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.contentManager = contentManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emails.setCellFactory(new EmailCellFactory(userService));

        emails.setOnMouseClicked(this::openEmail);
        emails.setItems(FXCollections.observableArrayList(sessionService.getUser().getInboxEmails()));
        searchButton.setOnAction(this::filterInbox);
        filterComboBox.setItems(FXCollections.observableArrayList(InboxFilters.values()));
        filterComboBox.valueProperty().addListener(this::updateInboxCombo);
    }

    private void updateInboxCombo(Observable observable) {
        emails.setItems(switch (filterComboBox.getSelectionModel().getSelectedItem()) {
            case ALL -> FXCollections.observableArrayList(sessionService.getUser().getInboxEmails());
            case SPAM -> userService.getSpamEmails();
            case STARRED -> userService.getStarredMessages();
        });
    }

    private void openEmail(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            Email em = emails.getSelectionModel().getSelectedItem();
            if(em != null) {
                contentManager.proceedTo(Contents.EMAIL, em);
            }
        }
    }

    void filterInbox(ActionEvent actionEvent) {
        String filter = searchBar.getText();
        if(Objects.equals(previousFilter, filter))
            return;

        emails.setItems(userService.getFilteredInbox(sessionService.getUser().getId(), filter));
        previousFilter = filter;
    }
}
