package com.email.sys;

import lombok.Getter;

import java.util.List;

@Getter
public enum Contents {
    INBOX("/inbox"),
    SEND("/send"),
    SENT("/sent"),
    SETTINGS("/settings"),
    EMAIL("/email"),
    STARRED("/starred"),
    FORWARD("/forward");

    private final String path;

    /**
     * Primary contents are contents which should be the first in the content history in order not to
     * record them in the history
     * @return list of all primary contents
     */
    public static List<Contents> getPrimaryContents(){
        return List.of(INBOX, SEND, SEND, SETTINGS);
    }

    Contents(String path) {
        this.path = "/mainPageContent" + path + ".fxml";
    }
}
