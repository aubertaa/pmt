package fr.aaubert.pmtbackend.service;

import fr.aaubert.pmtbackend.model.User;
import java.util.List;

public interface UserService {

    Long saveUser(User user);
    //List<User> getAllUsers();
    //User getUserById(Long id);
    //User getUserByUsername(String username);
    //User getUserByEmail(String email);
    //User updateUser(User user, Long id);
    void deleteUser(Long id);


}
