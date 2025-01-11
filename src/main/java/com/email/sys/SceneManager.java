package com.email.sys;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class SceneManager {
    private final EnumMap<Views, Scene> scenes = new EnumMap<>(Views.class);
    private final SpringFXMLLoader loader;
    private Stage stage;

    @Autowired
    public SceneManager(SpringFXMLLoader loader) {
        this.loader = loader;
    }

    public void switchScene(Views state){
        scenes.computeIfAbsent(state, loader::loadScene);
        stage.setScene(scenes.get(state));
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
