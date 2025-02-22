package com.email.sys;

import com.email.sys.controllers.Resettable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ElementsUtils {

    private ElementsUtils() {
    }

    public static void showLabel(Label label, String message){
        label.setVisible(true);
        label.setManaged(true);
        label.setText(message);
    }

    public static void hideNode(Node node){
        node.setVisible(false);
        node.setManaged(false);
    }

    public static void showCorrespondingLabel(Result<?> result, Label success, Label fail){
        showCorrespondingLabel(result, success, fail, null);
    }

    public static void showCorrespondingLabel(Result<?> result, Label success, Label fail, Resettable resettable){
        String message = result.getMessage();
        if(result.hasError()){
            hideNode(success);
            showLabel(fail, message);
        }else{
            hideNode(fail);
            showLabel(success, message);

            if(resettable != null)
                resettable.reset();
        }
        // Trigger parent layout resize after showing/hiding labels
        // Assuming success and fail are inside a parent layout (e.g., VBox, HBox)
        if (success.getParent() != null) {
            success.getParent().requestLayout();
        }
        if (fail.getParent() != null) {
            fail.getParent().requestLayout();
        }
    }
}
