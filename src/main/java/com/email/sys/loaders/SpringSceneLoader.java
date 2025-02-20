package com.email.sys.loaders;

import com.email.sys.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Component
public class SpringSceneLoader implements Loader<Scene, Views> {

    ConfigurableApplicationContext springContext;

    @Autowired
    public SpringSceneLoader(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public Scene load(Views view) {
        try {
            URL resource = SpringFXMLLoader.class.getResource(Objects.requireNonNull(view).getPath());
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(resource));
            loader.setControllerFactory(springContext::getBean);
            return new Scene(loader.load());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Error loading FXML file: " + view.name(), ex);
        }
    }
}
