package com.email.sys;

import com.email.sys.loaders.SpringSceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SceneManager {
    private final SpringSceneLoader springSceneLoader;
    private Stage stage;

    @Autowired
    public SceneManager(SpringSceneLoader springSceneLoader) {
        this.springSceneLoader = springSceneLoader;
    }

    public void switchScene(Views state){
        Scene scene = springSceneLoader.load(state);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
