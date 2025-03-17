package com.email.sys;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ForwardedMessageHandler {
    public static final String BODY_TEMPLATE =
            """
            ------ Forwarded message ------ 
            Date: {0} 
            From: {1}
            To: {2}
            
            
            """;
    public static final String HEADER_APPEND = "FWD: ";
    public Email handle(@NonNull Email email, User forwarder, User forwardTo) {
        Email em = new Email();
        em.setHeader((email.getHeader().startsWith(HEADER_APPEND) ? "" : HEADER_APPEND) + email.getHeader());
        em.setText(MessageFormat.format(BODY_TEMPLATE,
                email.getSendDate().toString(),
                email.getSender().getEmail(),
                email.getReceiver().getEmail()
                ) + email.getText()
        );
        em.setSender(forwarder);
        em.setReceiver(forwardTo);

        return em;
    }
}
