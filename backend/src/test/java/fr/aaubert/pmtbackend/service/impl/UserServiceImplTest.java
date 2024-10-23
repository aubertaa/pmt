package fr.aaubert.pmtbackend.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testDeleteUser() {
        // Test case 1: Deleting an existing user
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setUserName("john.doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSaveUser() {
        // Test case 1: Saving a new user
        User user = new User();
        user.setUserName("john.doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setUserId(456L);

        when(userRepository.save(user)).thenReturn(user);

        Long savedUser = userService.saveUser(user);
        assertEquals(456L, savedUser);

    }

    @Test
    void testGetUserByUserName() {
        // Test case 1: Saving a new user
        User user = new User();
        user.setUserName("john.doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setUserId(456L);

        when(userRepository.findByUsername("john.doe")).thenReturn(user);

        User foundUser = userService.getUserByUserName("john.doe");
        assertEquals(user, foundUser);

    }



}
