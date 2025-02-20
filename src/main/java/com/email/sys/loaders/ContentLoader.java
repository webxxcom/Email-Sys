package com.email.sys.loaders;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import com.email.sys.trackers.Tracker;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContentLoader implements ConfigurableLoader<Node, Contents> {
    private final SpringFXMLLoader springFXMLLoader;
    private final ContentArea contentArea;

    public ContentLoader(SpringFXMLLoader springFXMLLoader, ContentArea contentArea) {
        this.springFXMLLoader = springFXMLLoader;
        this.contentArea = contentArea;
    }

    @Override
    public <T> Node load(Contents content, T data){
        Objects.requireNonNull(content);

        Node load = springFXMLLoader.load(content.getPath(), data);
        contentArea.set(load);
        return load;
    }
}
