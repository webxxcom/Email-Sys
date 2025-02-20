package com.email.sys.trackers;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import com.email.sys.loaders.ContentLoader;
import javafx.scene.Node;
import org.springframework.stereotype.Component;

@Component
public class ContentManager {
    private final ContentLoader contentLoader;
    private final ContentTracker contentTracker;
    private final ContentArea contentArea;

    public ContentManager(ContentLoader contentLoader, ContentTracker contentTracker, ContentArea contentArea) {
        this.contentLoader = contentLoader;
        this.contentTracker = contentTracker;
        this.contentArea = contentArea;
    }

    public void goBack(){
        contentArea.set(contentTracker.back());
    }

    public void goForth(){
        contentArea.set(contentTracker.forth());
    }

    public void proceedTo(Contents content){
        proceedTo(content, null);
    }

    public <T> void proceedTo(Contents content, T configuration){
        if(Contents.getPrimaryContents().contains(content))
            contentTracker.forgetAll();
        contentTracker.remember(contentLoader.load(content, configuration));
    }
}
