package com.email.sys.controllers;

import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.entities.Email;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class InboxController implements Initializable {

    //Singletons
    UserService userService;
    SessionService sessionService;

    String previousFilter;

    @FXML public ListView<Email> emails;
    @FXML public TextField searchBar;
    @FXML public Button searchButton;

    @Autowired
    public InboxController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emails.setCellFactory(new EmailCellFactory(sessionService, this::openEmail));
        emails.setItems(userService.getInbox(sessionService.getUser().getId()));
        searchButton.setOnAction(this::filterInbox);
    }



    void filterInbox(ActionEvent actionEvent) {
        String filter = searchBar.getText();
        if(Objects.equals(previousFilter, filter))
            return;

        emails.setItems(userService.getFilteredInbox(sessionService.getUser().getId(), filter));
        previousFilter = filter;
    }

    void openEmail(MouseEvent mouseEvent){
        if(mouseEvent.getClickCount() == 2){

        }
    }
}
