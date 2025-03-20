package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.StageHolder;
import com.email.sys.entities.User;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class SettingsController implements Initializable {

    private final SessionService sessionService;
    private final UserService userService;
    private final User modifiedUser;
    private final StageHolder stageHolder;

    @FXML TextField usernameField;
    @FXML TextField emailField;
    @FXML CheckBox notificationsCheckBox;
    @FXML ComboBox<String> themeComboBox;
    @FXML TextArea signatureTextArea;
    @FXML Label avatarLabel;
    @FXML Button uploadAvatarButton;
    @FXML Label savingResultLabel;
    @FXML Button saveButton;
    @FXML Button cancelButton;

    public SettingsController(SessionService sessionService, UserService userService, StageHolder stageHolder) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.modifiedUser = new User(sessionService.getUser());
        this.stageHolder = stageHolder;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailField.setText(sessionService.getUser().getEmail());
        notificationsCheckBox.setSelected(sessionService.getUser().getUserSettings().isSendNotifications());

        uploadAvatarButton.setOnAction(this::chooseAvatar);
        notificationsCheckBox.setOnAction(this::tickNotifications);
        saveButton.setOnAction(this::save);
    }

    private void tickNotifications(ActionEvent actionEvent) {
        modifiedUser.getUserSettings().setSendNotifications(notificationsCheckBox.isSelected());
    }

    private void save(ActionEvent actionEvent) {
        /* Do not execute db query if nothing was modified */
        if (modifiedUser.equals(sessionService.getUser()))
            return;

        Result<User> result = userService.save(modifiedUser);
        sessionService.setUser(result.data());
        ElementsUtils.showLabel(savingResultLabel, result.message());
    }

    private void chooseAvatar(Event evt) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.PNG"));

        File f = fc.showOpenDialog(stageHolder.getStage());
        if (f != null) {
            avatarLabel.setText(f.getName());
            try (var fis = new FileInputStream(f)) {
                modifiedUser.setAvatar(fis.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException("There was an error in reading the selected file: " + f.getPath());
            }
        }
    }
}
