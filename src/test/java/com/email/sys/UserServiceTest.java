//package com.email.sys;
//
//import com.email.sys.entities.Email;
//import com.email.sys.entities.User;
//import com.email.sys.repositories.UserRepository;
//import com.email.sys.services.EmailService;
//import com.email.sys.services.UserService;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import jakarta.persistence.TypedQuery;
//import javafx.collections.ObservableList;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class UserServiceTest {
//
//    @MockitoBean
//    private EntityManager em;
//
//    @Autowired
//    private UserService userService;
//
//    @MockitoBean
//    private UserRepository userRepository;
//
//    @Mock
//    private TypedQuery<User> typedQuery;
//
//    @Mock
//    private Query query;
//    @Autowired
//    private EmailService emailService;
//
//    @Test
//    void testUserFoundForEmail(){
//        java.lang.String email = "someEmail@email.com";
//        User mockUser = new User(email, "password");
//
//        when(userRepository.findByEmail(anyString())).thenReturn(mockUser);
//
//        Optional<User> optionalUser = userService.getForEmail(email);
//
//        assertTrue(optionalUser.isPresent());
//
//        User user = optionalUser.get();
//        assertEquals(mockUser.getEmail(), user.getEmail());
//        assertEquals(mockUser.getPassword(), user.getPassword());
//    }
//
//    @Test
//    void testUserNotFoundForEmail(){
//        when(typedQuery.getSingleResultOrNull()).thenReturn(null);
//
//        Optional<User> forEmail = userService.getForEmail("");
//        assertTrue(forEmail.isEmpty());
//    }
//
//    @Test
//    void testGetFilteredInbox(){
//        java.lang.String filterText = "important";
//
//        Email email1 = new Email();
//        email1.setText("Some important message");
//
//        Email email2 = new Email();
//        email2.setText("Random spam");
//
//        List<Email> emails = List.of(email1, email2);
//
//        TypedQuery<Email> emailTypedQuery = mock(TypedQuery.class);
//        when(em.createQuery(anyString(), eq(Email.class))).thenReturn(emailTypedQuery);
//        when(emailTypedQuery.getResultList()).thenReturn(emails);
//
//        User user = new User();
//        user.setId(1L);
//        user.setInboxEmails(emails);
//        ObservableList<Email> filteredInbox = emailService.getFilteredInbox(
//                user, filterText
//        );
//        assertFalse(filteredInbox.isEmpty());
//        assertFalse(filteredInbox.filtered(
//                el -> el.getText().contains(filterText)).isEmpty()
//        );
//    }
//
//    @Test
//    void testTryLogInSuccess(){
//        java.lang.String email = "email@email.com";
//        java.lang.String password = "password";
//        User mockUser = new User();
//        mockUser.setEmail(email);
//        mockUser.setPassword(password);
//
//        when(userRepository.findByEmail(email)).thenReturn(mockUser);
//
//        Result<User> result = userService.tryLogIn(email, password);
//        assertTrue(result.isSuccess());
//    }
//
//    @Test
//    void testLogInNoEmail(){
//        java.lang.String email = "";
//
//        when(typedQuery.getSingleResultOrNull()).thenReturn(null);
//        Result<User> result = userService.tryLogIn(email, "somePass");
//        assertTrue(result.hasError(), "When user tries to log in with non-existent email then the result must be an error");
//    }
//
//    @Test
//    void testLogInWrongPassword(){
//        java.lang.String email = "email@email.com";
//        java.lang.String incorrectPassword = "192837465";
//        User u = new User();
//        u.setEmail(email);
//        u.setPassword("123456789");
//
//        when(typedQuery.getSingleResultOrNull()).thenReturn(u);
//        Result<User> result = userService.tryLogIn(email, incorrectPassword);
//        assertTrue(result.hasError());
//    }
//}
