package com.email.sys.controllers;

import com.email.sys.loaders.SpringFXMLLoader;
import com.email.sys.cell.factories.EmailCellFactory;
import com.email.sys.entities.Email;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.collections.FXCollections;
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
    private final MainPageController mainPageController;
    UserService userService;
    SpringFXMLLoader loader;

    @FXML private TextField searchBar;
    @FXML private Button searchButton;
    @FXML private ListView<Email> sentEmails;

    public SentController(SessionService sessionService, UserService userService, SpringFXMLLoader loader, MainPageController mainPageController) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.loader = loader;
        this.mainPageController = mainPageController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sentEmails.setCellFactory(new EmailCellFactory());
        sentEmails.setItems(FXCollections.observableArrayList(sessionService.getUser().getSentEmails()));
    }
}
