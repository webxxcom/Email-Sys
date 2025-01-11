package com.email.sys;

public enum Views {
    LOG_IN("/logIn"),
    SIGN_UP("/signUp"),
    MAIN_PAGE("/mainPage"),
    INBOX("/inbox");

    final String path;

    Views(String path) {
        this.path = path + ".fxml";
    }
}
