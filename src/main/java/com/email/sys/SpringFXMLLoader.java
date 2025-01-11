package com.email.sys;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class SpringFXMLLoader {
    ConfigurableApplicationContext springContext;

    @Autowired
    public SpringFXMLLoader(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    public Scene loadScene(Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SpringFXMLLoader.class.getResource(view.path)));
            loader.setControllerFactory(springContext::getBean);
            return new Scene(loader.load());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Error loading FXML file: " + view.path, ex);
        }
    }

    public Node loadFXML(String fileName){
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SpringFXMLLoader.class.getResource(fileName)));
            loader.setControllerFactory(springContext::getBean);
            return loader.load();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error loading FXML file: " + fileName, ex);
        }
    }
}
