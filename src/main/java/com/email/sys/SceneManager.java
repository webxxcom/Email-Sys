package com.email.sys;

import com.email.sys.loaders.SpringSceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter @Setter
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
}
