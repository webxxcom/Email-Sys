package com.email.sys.loaders;

import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

@Component
public class ContentViewLoader {
    public enum Contents{
        EMAIL("/email");

        final String path;
        Contents(String path) {
            this.path = "/mainPageContent" + path + ".fxml";
        }
    }

    private final SpringFXMLLoader loader;
    public ContentViewLoader(SpringFXMLLoader loader) {
        this.loader = loader;
    }

    public void loadContent(Pane contentPane, Contents content){
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loader.loadFXML(content.path));
    }
}
