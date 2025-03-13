package com.email.sys.trackers;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import com.email.sys.loaders.ContentLoader;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;

@Component
public class ContentManager {
    private final ContentLoader contentLoader;
    private final ContentTracker contentTracker;
    private final ContentArea contentArea;
    private @Getter Contents currentContent;

    public ContentManager(ContentLoader contentLoader, ContentTracker contentTracker, ContentArea contentArea) {
        this.contentLoader = contentLoader;
        this.contentTracker = contentTracker;
        this.contentArea = contentArea;
    }

    public void goBack() {
        Map.Entry<Contents, Node> back = contentTracker.back();
        if(back != null) {
            contentArea.set(back.getValue());
            currentContent = back.getKey();
        }
    }

    public void goForth() {
        Map.Entry<Contents, Node> forth = contentTracker.forth();
        if(forth != null) {
            contentArea.set(forth.getValue());
            currentContent = forth.getKey();
        }
    }

    public void proceedTo(@NonNull Contents content) {
        if (content.equals(currentContent)) {
            return;
        } else if (Contents.getPrimaryContents().contains(content)) {
            contentTracker.forgetAll();
        }
        contentTracker.remember(new AbstractMap.SimpleEntry<>(content, contentLoader.load(content)));
        currentContent = content;
    }
}
