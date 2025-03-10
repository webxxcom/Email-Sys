package com.email.sys.trackers;

import com.email.sys.ContentArea;
import com.email.sys.Contents;
import com.email.sys.loaders.ContentLoader;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ContentManager {
    private final ContentLoader contentLoader;
    private final ContentTracker contentTracker;
    private final @Getter ContentArea contentArea;
    private @Getter Contents currentContent;

    public ContentManager(ContentLoader contentLoader, ContentTracker contentTracker, ContentArea contentArea) {
        this.contentLoader = contentLoader;
        this.contentTracker = contentTracker;
        this.contentArea = contentArea;
    }

    public void goBack() {
        contentArea.set(contentTracker.back());
    }

    public void goForth() {
        contentArea.set(contentTracker.forth());
    }

    public void proceedTo(@NonNull Contents content) {
        if (content.equals(currentContent)) {
            return;
        } else if (Contents.getPrimaryContents().contains(content)) {
            contentTracker.forgetAll();
        }
        contentTracker.remember(contentLoader.load(content));

        currentContent = content;
    }
}
