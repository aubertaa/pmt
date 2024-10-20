package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.UserRepository;
import fr.aaubert.pmtbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.util.List;
//import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long saveUser(User user) {
        // return user id after having saved it
        User new_user = userRepository.save(user);
        return new_user.getUserId();
    }
/*
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }
*/

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
