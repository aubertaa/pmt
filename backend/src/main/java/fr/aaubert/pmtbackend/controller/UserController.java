package fr.aaubert.pmtbackend.controller;
import fr.aaubert.pmtbackend.model.User;
import fr.aaubert.pmtbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public User getUserByUserName(@Param("userName") String userName){
        return userService.getUserByUserName(userName);
    }

}
