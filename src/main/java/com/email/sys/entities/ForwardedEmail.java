package com.email.sys.entities;

import com.email.sys.entities.composite.ForwardedEmailsId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@Entity
@Immutable
public class ForwardedEmail {

    @EmbeddedId
    ForwardedEmailsId forwardedEmailsId;

    @Column(nullable = false)
    LocalDateTime forwardedOn;

    @PrePersist
    public void prePersisting(){
        if(forwardedOn == null) forwardedOn = LocalDateTime.now();
    }

    public ForwardedEmail(@NonNull Email email, @NonNull User sender, @NonNull User receiver) {
        this.forwardedEmailsId = new ForwardedEmailsId(email.getId(), sender.getId(), receiver.getId());
    }
}
