package fr.aaubert.pmtbackend.controller;
import fr.aaubert.pmtbackend.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class RoleController {
    //will have here API endpoints for roles

    @GetMapping("/roles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserRole> getAllRoles() {
        return List.of(UserRole.values());
    }


}
