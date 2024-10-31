package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.exceptions.EntityDontExistException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

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
        user.setEmail("john.doe@example.com2");
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
        user.setEmail("john.doe@example.com3");
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
        user.setEmail("john.doe@example.com4");
        user.setPassword("password");
        user.setUserId(456L);

        when(userRepository.findByUsername("john.doe")).thenReturn(user);

        User foundUser = userService.getUserByUserName("john.doe");
        assertEquals(user, foundUser);

    }


    @Test
    void setNotificationStatusForUserId_withValidUserId_updatesNotificationStatus() {
        Long userId = 1L;
        Boolean notificationsActive = true;

        userService.setNotificationStatusForUserId(userId, notificationsActive);

        verify(userRepository, times(1)).setNotificationStatusForUserId(userId, notificationsActive);
    }


    @Test
    void testGetAllUsers() {
        // Test case 1: Saving a new user
        User user = new User();
        user.setUserName("john.doe");
        user.setEmail("mail@mail");
        user.setPassword("password");
        user.setUserId(456L);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));

    }





}
