package com.email.sys.repositories;

import com.email.sys.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select u from User u where u.email like :emLike%")
    Collection<User> getUsersWithEmailLike(String emLike);

    @Cacheable("knownUsersFor")
    @Query("select distinct e.receiver from Email e where e.sender = :user order by e.receiver.email")
    Collection<User> getKnownUsersFor(User user);

    @Query("select u.email from User u where u.id = :id")
    String findEmailById(Long id);
}
