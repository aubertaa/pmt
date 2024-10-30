package fr.aaubert.pmtbackend.service.impl;

import fr.aaubert.pmtbackend.exceptions.EntityDontExistException;
import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.repository.UserRepository;
import fr.aaubert.pmtbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    @Override
    public User getUserByUserName(String userName) {

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userName));

        // On trouve le user
        if(user.isPresent()) {
            return user.get();
        }

        //sinon on renvoie une exception
        throw new EntityDontExistException();

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void setNotificationStatusForUserId(Long userId, Boolean notificationsActive) {
        userRepository.setNotificationStatusForUserId(userId, notificationsActive);
    }

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityDontExistException::new);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
