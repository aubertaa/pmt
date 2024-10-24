package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserName("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
    }

    @Test
    public void testFindByUsername() {

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userRepository.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUserName());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testFindByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(null);

        User foundUser = userRepository.findByUsername("unknownuser");

        assertNull(foundUser);
        verify(userRepository, times(1)).findByUsername("unknownuser");
    }
}
