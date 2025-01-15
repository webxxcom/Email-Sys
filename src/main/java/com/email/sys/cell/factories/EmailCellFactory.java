package com.email.sys.cell.factories;

import com.email.sys.entities.Email;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import org.assertj.core.internal.ChronoLocalDateTimeComparator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailCellFactory implements Callback<ListView<Email>, ListCell<Email>> {

    @Override
    public ListCell<Email> call(ListView<Email> emailListView) {
        return new ListCell<>(){
            private static final String ARIAL = "Arial";
            private static final int MAX_TEXT_LENGTH = 100;

            @Override
            protected void updateItem(Email email, boolean b) {
                super.updateItem(email, b);
                if (b || email == null) {
                    setGraphic(null);
                } else {
                    HBox cellLayout = new HBox(10);
                    cellLayout.setPadding(new Insets(10));
                    cellLayout.setAlignment(Pos.CENTER_LEFT);

                    Circle avatar = new Circle(20, Color.LIGHTBLUE);
                    avatar.setStroke(Color.DARKBLUE);
                    avatar.setStrokeWidth(2);

                    VBox contentBox = new VBox(5);
                    contentBox.setAlignment(Pos.CENTER_LEFT);

                    Label headerLabel = new Label(email.getHeader());
                    headerLabel.setFont(Font.font(ARIAL, FontWeight.BOLD, 14));
                    headerLabel.setTextFill(Color.DARKBLUE);

                    String emailText = email.getText();
                    Label textLabel = new Label(emailText.length() > MAX_TEXT_LENGTH ? emailText.substring(0, MAX_TEXT_LENGTH - 3) + "..." : emailText);
                    textLabel.setFont(Font.font(ARIAL, 12));
                    textLabel.setTextFill(Color.GRAY);
                    textLabel.setWrapText(true);
                    textLabel.setMaxWidth(300);

                    contentBox.getChildren().addAll(headerLabel, textLabel);

                    // Set date
                    Label dateLabel;

                    LocalDateTime emailSendDate = email.getSendDate();
                    if(emailSendDate.isBefore(LocalDateTime.now().minusDays(1))){
                        dateLabel = new Label(emailSendDate.toLocalDate().toString());
                    } else{
                        dateLabel = new Label(emailSendDate.toString().replace('T', ' '));
                    }
                    dateLabel.setFont(Font.font(ARIAL, FontPosture.ITALIC, 12));
                    dateLabel.setTextFill(Color.DARKGRAY);

                    cellLayout.getChildren().addAll(avatar, contentBox, dateLabel);
                    HBox.setHgrow(contentBox, Priority.ALWAYS);

                    setGraphic(cellLayout);
                }
            }
        };
    }
}
