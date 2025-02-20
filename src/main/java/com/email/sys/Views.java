package com.email.sys;

public enum Views {
    LOG_IN("/logIn"),
    SIGN_UP("/signUp"),
    MAIN_PAGE("/mainPage");

    final String path;

    Views(String path) {
        this.path = path + ".fxml";
    }

    public String getPath() {
        return path;
    }
}
