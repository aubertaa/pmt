package fr.aaubert.pmtbackend.repository;

import fr.aaubert.pmtbackend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User user = userRepository.findByUsername("john_doe");
        assertNotNull(user);
        assertEquals("john_doe", user.getUserName());
    }

   /* @Test
    public void testFindByEmail() {
        User user = userRepository.findByEmail("john.doe@example.com");
        assertNotNull(user);
        assertEquals("john.doe@example.com", user.getEmail());
    }*/

}