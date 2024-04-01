package com.udea.edyl.EDyL.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udea.edyl.EDyL.service.UserService;
import com.udea.edyl.EDyL.web.dto.UserDto;

@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws Exception {
        if (userDto == null) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        UserDto resp;
        try {
            resp = userService.saveUser(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/get-user") 
    public ResponseEntity<?> getUser(@RequestParam Long userId) {
        UserDto resp = userService.getUser(userId);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exist");
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();

        return ResponseEntity.ok(userDtos);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        Boolean resp = userService.deleteUser(userId);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exist");
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody UserDto updatedUser) {
        Boolean resp = userService.editUser(userId, updatedUser);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exist");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/exist-email")
    public ResponseEntity<Boolean> existEmail(@RequestParam String email) {
        Boolean resp = userService.existEmail(email);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-user-emails")
    public ResponseEntity<?> getEmails() {
        List<String> emails = userService.getEmails();

        return ResponseEntity.ok(emails);
    }

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
        Boolean loginSuccessful = userService.verifyLogin(email, password);

        return ResponseEntity.ok(loginSuccessful);
    }
}
