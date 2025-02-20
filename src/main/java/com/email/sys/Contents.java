package com.email.sys;

import java.util.List;

public enum Contents {
    INBOX("/inbox"),
    SEND("/send"),
    SENT("/sent"),
    SETTINGS("/settings"),
    EMAIL("/email");

    final String path;

    public String getPath() {
        return path;
    }

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
