package com.email.sys;

import com.email.sys.controllers.Resettable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ElementsUtils {

    private ElementsUtils() {
    }

    public static void showLabel(Label label, String message) {
        label.setVisible(true);
        label.setManaged(true);
        label.setText(message);
    }

    public static void hideNode(Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    public static void showCorrespondingLabel(Result<?> result, Label success, Label fail) {
        showCorrespondingLabel(result, success, fail, null);
    }

    public static void showCorrespondingLabel(Result<?> result, Label success, Label fail, Resettable resettable) {
        String message = result.message();
        if (result.hasError()) {
            hideNode(success);
            showLabel(fail, message);
        } else {
            hideNode(fail);
            showLabel(success, message);

            if (resettable != null)
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

    public static <T> void addDebouncingActionEventForProperty(Property<T> property, int delay, EventHandler<ActionEvent> eventHandler){
         property.addListener(new ChangeListener<T>() {
             private final Timeline timeline = new Timeline(new KeyFrame(
                     Duration.millis(delay),
                     eventHandler)
             );

             @Override
             public void changed(ObservableValue<? extends T> observableValue, T t, T t1) {
                timeline.playFromStart();
             }
         });
    }
}
