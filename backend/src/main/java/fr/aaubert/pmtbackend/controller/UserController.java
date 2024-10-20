package fr.aaubert.pmtbackend.controller;
import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    //will have here API endpoints for user management

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long register(@RequestBody User user){
        //register user
        return userService.saveUser(user);
    }




}
