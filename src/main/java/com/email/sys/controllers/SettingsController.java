package com.email.sys.controllers;

import com.email.sys.ElementsUtils;
import com.email.sys.Result;
import com.email.sys.SceneManager;
import com.email.sys.entities.User;
import com.email.sys.services.SessionService;
import com.email.sys.services.UserService;
import com.sun.javafx.iio.common.ImageLoaderImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.imageio.stream.ImageInputStreamImpl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class SettingsController implements Initializable {

    private final SessionService sessionService;
    private final SceneManager sceneManager;
    private final UserService userService;
    private final User modifiedUser;

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

    public SettingsController(SessionService sessionService, SceneManager sceneManager, UserService userService) {
        this.sessionService = sessionService;
        this.sceneManager = sceneManager;
        this.userService = userService;
        this.modifiedUser = new User(sessionService.getUser());
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
        if(modifiedUser.equals(sessionService.getUser()))
            return;

        Result<User> result = userService.saveSettings(modifiedUser);
        sessionService.setUser(result.getData());
        ElementsUtils.showLabel(savingResultLabel, result.getMessage());
    }

    private void chooseAvatar(Event evt) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.PNG"));

        File f = fc.showOpenDialog(sceneManager.getStage());
        if (f != null) {
            avatarLabel.setText(f.getName());
            try (var fis = new FileInputStream(f)){
                modifiedUser.setAvatar(fis.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException("There was an error in reading the selected file: " + f.getPath());
            }
        }
    }
}
