package com.email.sys;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    @Bean
    public EntityManager entityManager() {
        return Persistence.createEntityManagerFactory("emailSysUnit").createEntityManager();
    }
}
