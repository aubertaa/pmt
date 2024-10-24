package fr.aaubert.pmtbackend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void testGetEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testGetPassword() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testGetUserId() {
        User user = new User();
        user.setUserId(1L);
        assertEquals(1L, user.getUserId());
    }

    @Test
    void testGetUserName() {
        User user = new User();
        user.setUserName("John Doe");
        assertEquals("John Doe", user.getUserName());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUserId(123L);
        assertEquals("User [userId=123, userName=John Doe, email=test@example.com, password=password123]", user.toString());
    }

    @Test
    void testSetters() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        assertEquals("John Doe", user.getUserName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }
}