package com.email.sys.controllers;

import com.email.sys.SceneManager;
import com.email.sys.loaders.SpringFXMLLoader;
import com.email.sys.Views;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class MainPageController implements Initializable {
    private final SceneManager sceneManager;

    private enum Contents {
        INBOX("/inbox"),
        SEND("/send"),
        SENT("/sent"),
        SETTINGS("/settings");

        final String path;

        Contents(String path) {
            this.path = "/mainPageContent" + path + ".fxml";
        }
    }

    private final SpringFXMLLoader loader;
    private final UserService userService;
    private final SessionService sessionService;

    @FXML Pane contentArea;
    @FXML VBox navigationPanel;
    @FXML Button inboxButton;
    @FXML Button sendButton;
    @FXML Button sentButton;
    @FXML Button settingsButton;
    @FXML Button logOutButton;

    @Autowired
    public MainPageController(UserService userService, SpringFXMLLoader loader, SessionService sessionService, SceneManager sceneManager) {
        this.userService = userService;
        this.loader = loader;
        this.sessionService = sessionService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inboxButton.setOnAction(evt -> switchToInbox());
        sendButton.setOnAction(evt -> switchToSend());
        sentButton.setOnAction(evt -> switchToSent());
        settingsButton.setOnAction(evt -> switchToSettings());
        logOutButton.setOnAction(evt -> logOut());
    }


    public void switchToSent(){
        showContent(Contents.SENT);
    }
    public void switchToSend(){
        showContent(Contents.SEND);
    }
    public void switchToInbox(){
        showContent(Contents.INBOX);
    }
    public void switchToSettings() {
        showContent(Contents.SETTINGS);
    }

    private void showContent(Contents content){
        contentArea.getChildren().clear();
        contentArea.getChildren().add(loader.loadFXML(content.path));

    }

    public void logOut(){
        sessionService.clean();
        sceneManager.switchScene(Views.LOG_IN);
    }

}
