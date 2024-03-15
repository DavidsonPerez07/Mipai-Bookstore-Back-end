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
    public ResponseEntity<?> showStudent(@RequestParam Long userId) {
        UserDto resp;
        resp = userService.getUser(userId);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exist");
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> showAllBooks() {
        List<UserDto> userDtos = userService.getAllUsers();

        return ResponseEntity.ok(userDtos);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        Boolean resp;
        resp = userService.deleteUser(userId);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exist");
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody UserDto updatedUser) {
        Boolean resp;
        resp = userService.editUser(userId, updatedUser);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user does't exist");
        }

        return ResponseEntity.ok().build();
    }
}
