package com.email.sys.repositories;

import com.email.sys.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  {

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean userWithEmailExists(String email);
}
