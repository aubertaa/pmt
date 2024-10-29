package fr.aaubert.pmtbackend.controller;
import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class UserController {
    //will have here API endpoints for user management

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long register(@RequestBody @Valid User user){
       return userService.saveUser(user);
    }


    @GetMapping("/user")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getUserByUserName(@Param("userName") String userName){
        User user =  userService.getUserByUserName(userName);

        // Build response map with userId included
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("userName", user.getUserName());
        response.put("email", user.getEmail());
        response.put("password", user.getPassword());
        response.put("notifications", user.getNotifications());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Map<String, Object>>> getUsers(){
        List<User> users = userService.getAllUsers();

        // Build response map with userId included
        List<Map<String, Object>> response = users.stream().map(user -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", user.getUserId());
            userMap.put("userName", user.getUserName());
            userMap.put("email", user.getEmail());
            userMap.put("notifications", user.getNotifications());
            return userMap;
        }).toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Long> updateUser(@RequestBody @Valid User user) {
        Long updatedUserId = userService.updateUser(user);
        return ResponseEntity.ok(updatedUserId);
    }



}
