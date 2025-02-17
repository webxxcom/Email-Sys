package com.email.sys;

import com.email.sys.loaders.SpringFXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        stage.centerOnScreen();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
