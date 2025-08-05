package estoque.estoque.controller;

import estoque.estoque.dto.UserDTO;
import estoque.estoque.model.User;
import estoque.estoque.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody UserDTO dto) {
        User saved = userService.registerAdmin(dto);
        return ResponseEntity.status(201).body(saved);
    }
}
