package com.email.sys.loaders;

import com.email.sys.configurators.ConfigStorage;
import com.email.sys.configurators.Configurator;
import jakarta.annotation.Nullable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class SpringFXMLLoader implements ConfigurableLoader<Node, String> {
    ConfigurableApplicationContext springContext;
    List<Configurator> configurators;

    @Autowired
    public SpringFXMLLoader(ConfigurableApplicationContext springContext, List<Configurator> configurators) {
        this.springContext = springContext;
        this.configurators = configurators;
    }

    @Override
    public Node load(String fileName, @Nullable ConfigStorage data){
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SpringFXMLLoader.class.getResource(fileName)));
            loader.setControllerFactory(springContext::getBean);

            Node load = loader.load();
            if(data != null) {
                configurators.forEach(el ->
                        el.configure(loader.getController(), data));
            }
            return load;
        } catch (Exception ex) {
            throw new LoadingException("Error loading FXML file: " + fileName, ex);
        }
    }
}
