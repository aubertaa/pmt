package fr.aaubert.pmtbackend.service;

import fr.aaubert.pmtbackend.model.User;
import java.util.List;

public interface UserService {

    Long saveUser(User user);
    //List<User> getAllUsers();
    //User getUserById(Long userId);
    //User getUserByUserName(String userName);
    //User getUserByEmail(String email);
    //User updateUser(User user, Long userId);
    void deleteUser(Long id);


}
