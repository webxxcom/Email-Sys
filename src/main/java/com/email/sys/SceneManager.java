package com.email.sys;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class SceneManager {
    private final SpringFXMLLoader loader;
    private Stage stage;

    @Autowired
    public SceneManager(SpringFXMLLoader loader) {
        this.loader = loader;
    }

    public void switchScene(Views state){
        Scene scene = loader.loadScene(state);
        stage.setScene(scene);
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
