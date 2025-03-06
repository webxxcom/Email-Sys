package com.email.sys;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class ContentArea {
    private Pane contentPane;

    public void set(@NonNull Node node){
        contentPane.getChildren().clear();
        contentPane.getChildren().add(node);
    }
}
