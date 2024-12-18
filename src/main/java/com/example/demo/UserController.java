package com.example.demo;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "https://sorobooks-p83s.onrender.com")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        UserEntity savedUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserEntity userEntity) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Optional<UserEntity> userOptional = userService.findByEmail(userEntity.getEmail());
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("name", user.getName());
                response.put("email", user.getEmail());
                response.put("sex", user.getSex());
                response.put("profilePicture", user.getProfilePicture()); // Devolvendo os bytes da imagem
                response.put("phoneNumber", user.getPhoneNumber());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "User not found."));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password."));
        }
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // Método para editar o usuário
    @PutMapping("/users/{email}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String email, @RequestBody UserEntity updatedUser) {
        Optional<UserEntity> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity existingUser = userOptional.get();

            // Atualiza apenas os campos fornecidos
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getSex() != null) {
                existingUser.setSex(updatedUser.getSex());
            }
            if (updatedUser.getProfilePicture() != null) {
                existingUser.setProfilePicture(updatedUser.getProfilePicture());
            }
            if (updatedUser.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            }

            // Atualiza o usuário no banco
            UserEntity savedUser = userService.saveUser(existingUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    // Novo método para excluir o usuário
    @DeleteMapping("/users/{email}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String email) {
        Optional<UserEntity> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            userService.deleteByEmail(email);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found."));
        }
    }
}
