package com.email.sys.services;

import com.email.sys.PasswordAuthenticator;
import com.email.sys.Result;
import com.email.sys.entities.User;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordAuthenticator passwordAuthenticator;

    @Autowired
    public UserService(UserRepository userRepository, PasswordAuthenticator passwordAuthenticator) {
        this.userRepository = userRepository;
        this.passwordAuthenticator = passwordAuthenticator;
    }

    public Optional<User> getForEmail(java.lang.String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Result<User> trySignUp(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            return Result.ofError("User with such an email already exists");
        }

        return Result.ofSuccess(
                userRepository.save(new User(email, password)),
                "The registration was successful"
        );
    }

    public Result<User> tryLogIn(@NonNull String email, @NonNull String password) {
        if (email.isBlank()) {
            return Result.ofError("Please fill the email");
        } else if (password.isBlank()) {
            return Result.ofError("Please fill the password");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Result.ofError("Such email does not exist");
        }

        User user = optionalUser.get();
        if (!passwordAuthenticator.authorize(password, user.getPassword())) {
            return Result.ofError("Password is incorrect");
        }
        return Result.ofSuccess(user);
    }

    public Result<User> save(User user) {
        try {
            return Result.ofSuccess(
                    userRepository.save(user),
                    "Settings were successfully saved"
            );
        } catch (Exception e) {
            return Result.ofError("Settings were not saved because of some error");
        }
    }

    public ObservableList<User> searchForEmail(@NonNull String emLike) {
        return FXCollections.observableArrayList(userRepository.getUsersWithEmailLike(emLike));
    }

    public ObservableList<User> getUsersListFor(@NonNull User user) {
        return FXCollections.observableArrayList(userRepository.getKnownUsersFor(user));
    }

    public ObservableList<User> performUserSearch(String userEmail, User user) {
        Optional<User> forEmail = getForEmail(userEmail);
        if(forEmail.isPresent()) {
            return FXCollections.observableArrayList(forEmail.get());
        } else{
            return getUsersListFor(user);
        }
    }
}
