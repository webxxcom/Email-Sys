package com.email.sys.loaders;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import javafx.scene.Node;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ContentLoader implements Loader<Node, Contents> {
    private final SpringFXMLLoader springFXMLLoader;
    private final ContentArea contentArea;

    public ContentLoader(SpringFXMLLoader springFXMLLoader, ContentArea contentArea) {
        this.springFXMLLoader = springFXMLLoader;
        this.contentArea = contentArea;
    }

    @Override
    public Node load(@NonNull Contents content) {
        Node load = springFXMLLoader.load(content.getPath());
        contentArea.set(load);
        return load;
    }
}
