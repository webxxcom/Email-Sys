package com.email.sys.repositories;

import com.email.sys.entities.EmailContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailContentRepository extends JpaRepository<EmailContent, Long> {
}
