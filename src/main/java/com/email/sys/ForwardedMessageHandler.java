package com.email.sys;

import com.email.sys.entities.Email;
import com.email.sys.entities.EmailContent;
import com.email.sys.entities.ForwardedEmail;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ForwardedMessageHandler {
    public void handle(@NonNull Email email, @NonNull ForwardedEmail forwardedEmail) {
        EmailContent emailContent = email.getEmailContent();

        emailContent.setHeader(emailContent.getHeader());
        emailContent.setText(
                ("------ Forwarded message ------" +
                        "\nDate: " + forwardedEmail.getForwardedOn().toString() +
                        "\nFrom: " + email.getSender().getEmail() +
                        "\nTo: " + email.getReceiver().getEmail() + "\n\n")
                        .concat(emailContent.getText())
        );
    }
}
