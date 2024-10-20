package fr.aaubert.pmtbackend.init;

import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitData implements CommandLineRunner {

        private final UserRepository userRepository;

        @Override
        public void run(String... args) throws Exception {
                //init data
                User user = new User();
                user.setUserName("john_doe");
                user.setEmail("john.doe@example.com");
                user.setPassword("securepassword");
                userRepository.save(user);
        }
}
