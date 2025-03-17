package com.email.sys;

import com.email.sys.loaders.SpringSceneLoader;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SceneManager {
    private final SpringSceneLoader springSceneLoader;
    private final StageHolder stageHolder;

    @Autowired
    public SceneManager(SpringSceneLoader springSceneLoader, StageHolder stageHolder) {
        this.springSceneLoader = springSceneLoader;
        this.stageHolder = stageHolder;
    }

    public void goTo(Views state) {
        Scene scene = springSceneLoader.load(state);
        stageHolder.getStage().setScene(scene);
        stageHolder.getStage().centerOnScreen();
    }
}
