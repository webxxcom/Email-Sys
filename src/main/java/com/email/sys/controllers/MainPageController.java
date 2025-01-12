package com.email.sys.controllers;

import com.email.sys.SpringFXMLLoader;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import com.email.sys.entities.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class MainPageController {

    private enum Contents {
        INBOX("/inbox"),
        SEND("/send");

        final String path;

        Contents(String path) {
            this.path = "/mainPageContent" + path + ".fxml";
        }
    }

    private final SpringFXMLLoader loader;
    private final UserService userService;
    private final SessionService sessionService;

    @FXML
    Pane contentArea;
    @FXML VBox navigationPanel;

    @FXML Button inboxButton;
    @FXML Button sendButton;
    @FXML Button sentButton;
    @FXML Button logOutButton;

    @Autowired
    public MainPageController(UserService userService, SpringFXMLLoader loader, SessionService sessionService) {
        this.userService = userService;
        this.loader = loader;
        this.sessionService = sessionService;
    }


    @FXML
    public void switchToSent(){
        //TODO implement switch to sent
    }

    @FXML
    public void switchToSend(){
        showContent(Contents.SEND);
    }

    @FXML
    public void switchToInbox(){
        showContent(Contents.INBOX);
    }

    private void showContent(Contents content){
        contentArea.getChildren().clear();
        contentArea.getChildren().add(loader.loadFXML(content.path));
    }
}
