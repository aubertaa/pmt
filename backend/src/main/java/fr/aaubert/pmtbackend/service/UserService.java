package fr.aaubert.pmtbackend.service;

import fr.aaubert.pmtbackend.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    Long saveUser(User user);
    //List<User> getAllUsers();

    //User getUserByEmail(String email);

    //User updateUser(User user, Long userId);
    void deleteUser(Long id);

    User getUserByUserName(String userName);

    List<User> getAllUsers();

    Long updateUser(@Valid User user);

    //User getUserById(Long userId);
}
