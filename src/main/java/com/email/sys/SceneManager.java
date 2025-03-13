package com.email.sys;

import com.email.sys.loaders.SpringSceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SceneManager {
    private final SpringSceneLoader springSceneLoader;
    private @Getter @Setter Stage stage;

    @Autowired
    public SceneManager(SpringSceneLoader springSceneLoader) {
        this.springSceneLoader = springSceneLoader;
    }

    public void goTo(Views state) {
        Scene scene = springSceneLoader.load(state);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
