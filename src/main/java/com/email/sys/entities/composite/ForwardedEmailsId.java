package com.email.sys.entities.composite;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor
public class ForwardedEmailsId implements Serializable {
    @Serial
    private static final long serialVersionUID = 3620554448357362906L;

    private Long emailId;
    private Long forwarderId;
    private Long forwardedToId;
}
