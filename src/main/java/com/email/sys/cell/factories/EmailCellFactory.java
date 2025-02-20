package com.email.sys.cell.factories;

import com.email.sys.converters.ImageConverter;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.time.LocalDateTime;

public class EmailCellFactory implements Callback<ListView<Email>, ListCell<Email>> {
    @Override
    public ListCell<Email> call(ListView<Email> emailListView) {
        return new ListCell<>() {
            private static final String ARIAL = "Arial";
            private static final int MAX_TEXT_LENGTH = 100;

            private Label getHeaderLabel(Email email) {
                Label headerLabel = new Label(email.getHeader());
                headerLabel.setFont(Font.font(ARIAL, FontWeight.BOLD, 14));
                headerLabel.setTextFill(
                        email.getReceiver().getId().equals(email.getSender().getId())
                                ? Color.RED
                                : Color.DARKBLUE);
                return headerLabel;
            }

            private Label getTextLabel(Email email) {
                String emailText = email.getText();
                Label textLabel = new Label(emailText.length() > MAX_TEXT_LENGTH ? emailText.substring(0, MAX_TEXT_LENGTH - 3) + "..." : emailText);
                textLabel.setFont(Font.font(ARIAL, 12));
                textLabel.setTextFill(Color.GRAY);
                textLabel.setWrapText(true);
                textLabel.setMaxWidth(300);

                return textLabel;
            }

            private Label getDateLabel(Email email) {
                Label dateLabel;

                LocalDateTime emailSendDate = email.getSendDate();
                if (emailSendDate.isBefore(LocalDateTime.now().minusDays(1))) {
                    dateLabel = new Label(emailSendDate.toLocalDate().toString());
                } else {
                    dateLabel = new Label(emailSendDate.toString().replace('T', ' '));
                }
                dateLabel.setFont(Font.font(ARIAL, FontPosture.ITALIC, 12));
                dateLabel.setTextFill(Color.DARKGRAY);
                return dateLabel;
            }

            private Circle getAvatar(Email email) {
                Circle avatar = new Circle(20, Color.GRAY);
                avatar.setStroke(Color.DARKBLUE);
                avatar.setStrokeWidth(2);

                byte[] avatarBytes = email.getSender().getAvatarBytes();
                if (avatarBytes != null)
                    avatar.setFill(new ImagePattern(ImageConverter.fromBytesToImage(avatarBytes)));

                return avatar;
            }

            private Label getStarLabel(Email email) {
                Label starLabel = new Label(email.isStarred() ? "★" : "☆");
                starLabel.setFont(Font.font(ARIAL, 16));
                starLabel.setTextFill(email.isStarred() ? Color.GOLD : Color.GRAY);
                starLabel.setOnMouseClicked(evt -> {
                    email.toggleStarred();
                    starLabel.setText(email.isStarred() ? "★" : "☆");
                    starLabel.setTextFill(email.isStarred() ? Color.GOLD : Color.GRAY);
                });
                return starLabel;
            }

            private VBox getContentBox(Email email) {
                VBox contentBox = new VBox(5);
                contentBox.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(contentBox, Priority.ALWAYS);

                contentBox.getChildren().addAll(getHeaderLabel(email), getTextLabel(email));
                return contentBox;
            }

            private HBox getCellLayout(Email email) {
                HBox cellLayout = new HBox(10);
                cellLayout.setPadding(new Insets(10));
                cellLayout.setAlignment(Pos.CENTER_LEFT);

                cellLayout.getChildren().addAll(
                        getAvatar(email),
                        getContentBox(email),
                        getDateLabel(email),
                        getStarLabel(email)
                );

                return cellLayout;
            }

            @Override
            protected void updateItem(Email email, boolean empty) {
                super.updateItem(email, empty);
                if (empty || email == null) {
                    setGraphic(null);
                } else {
                    setGraphic(getCellLayout(email));
                }
            }
        };
    }
}
