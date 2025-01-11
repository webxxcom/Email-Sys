package com.email.sys;

import javafx.scene.control.Label;

public class ElementsUtils {

    private ElementsUtils() {
    }

    public static void showErrorLabel(Label label, String error){
        label.setManaged(true);
        label.setText(error);
    }

    public static void hideErrorLabel(Label label){
        label.setManaged(false);
    }

}
