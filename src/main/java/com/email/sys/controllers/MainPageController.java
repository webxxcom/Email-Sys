package com.email.sys.controllers;

import com.email.sys.Contents;
import com.email.sys.SceneManager;
import com.email.sys.Views;
import com.email.sys.services.SessionService;
import com.email.sys.trackers.ContentManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainPageController implements Initializable {

    private final SessionService sessionService;
    private final SceneManager sceneManager;
    private final ContentManager contentManager;

    @FXML Pane contentPane;
    @FXML VBox navigationPanel;
    @FXML Button inboxButton;
    @FXML Button sendButton;
    @FXML Button sentButton;
    @FXML Button settingsButton;
    @FXML Button logOutButton;

    @Autowired
    public MainPageController(SessionService sessionService, SceneManager sceneManager, ContentManager contentManager) {
        this.sessionService = sessionService;
        this.sceneManager = sceneManager;
        this.contentManager = contentManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contentManager.getContentArea().setContentPane(contentPane);

        inboxButton.setOnAction(evt -> contentManager.proceedTo(Contents.INBOX));
        sendButton.setOnAction(evt -> contentManager.proceedTo(Contents.SEND));
        sentButton.setOnAction(evt -> contentManager.proceedTo(Contents.SENT));
        settingsButton.setOnAction(evt -> contentManager.proceedTo(Contents.SETTINGS));
        logOutButton.setOnAction(this::logOut);
    }

    public void logOut(ActionEvent actionEvent){
        sessionService.clean();
        sceneManager.switchScene(Views.LOG_IN);
    }
}
