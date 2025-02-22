package com.email.sys;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

@Component
public class ContentArea {
    Pane contentPane;

    public void setContentPane(Pane contentPane) {
        this.contentPane = contentPane;
    }

    public void set(Node node){
        if(contentPane == null)
            throw new IllegalStateException("The content pane was not set during program execution");

        contentPane.getChildren().clear();
        contentPane.getChildren().add(node);
    }
}
