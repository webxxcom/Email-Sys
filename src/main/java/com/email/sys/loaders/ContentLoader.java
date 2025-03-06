package com.email.sys.loaders;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import com.email.sys.configurators.ConfigStorage;
import jakarta.annotation.Nullable;
import javafx.scene.Node;
import lombok.NonNull;
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
    public Node load(@NonNull Contents content, @Nullable ConfigStorage data){
        Node load = springFXMLLoader.load(content.getPath(), data);
        contentArea.set(load);
        return load;
    }
}
