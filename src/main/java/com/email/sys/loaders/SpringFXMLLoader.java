package com.email.sys.loaders;

import com.email.sys.controllers.DataInjectable;
import jakarta.annotation.Nullable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SpringFXMLLoader implements ConfigurableLoader<Node, String> {
    ConfigurableApplicationContext springContext;

    @Autowired
    public SpringFXMLLoader(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public <T> Node load(String fileName, @Nullable T data){
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SpringFXMLLoader.class.getResource(fileName)));
            loader.setControllerFactory(springContext::getBean);

            Node load = loader.load();

            if(data != null) {
                try {
                    DataInjectable<T> di = loader.getController();
                    di.inject(data);
                    di.init();
                } catch (Exception e) {
                    throw new LoadingException("The controller for the " + fileName + " is not injectable", e);
                }
            }

            return load;
        } catch (Exception ex) {
            throw new LoadingException("Error loading FXML file: " + fileName, ex);
        }
    }
}
