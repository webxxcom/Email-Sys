package com.email.sys.trackers;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import com.email.sys.configurators.ConfigStorage;
import com.email.sys.loaders.ContentLoader;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ContentManager {
    private final ContentLoader contentLoader;
    private final ContentTracker contentTracker;
    private final @Getter ContentArea contentArea;

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

    public void proceedTo(@NonNull Contents content){
        proceedTo(content, null);
    }

    public void proceedTo(@NonNull Contents content, ConfigStorage configuration){
        if(Contents.getPrimaryContents().contains(content))
            contentTracker.forgetAll();
        contentTracker.remember(contentLoader.load(content, configuration));
    }
}
