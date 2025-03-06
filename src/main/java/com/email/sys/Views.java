package com.email.sys;

import lombok.Getter;

@Getter
public enum Views {
    LOG_IN("/logIn"),
    SIGN_UP("/signUp"),
    MAIN_PAGE("/mainPage");

    private final String path;

    Views(String path) {
        this.path = path + ".fxml";
    }

}
