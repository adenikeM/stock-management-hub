package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.User;
import com.springboot.stockmanagementhub.model.dto.ErrorResponse;
import com.springboot.stockmanagementhub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.springboot.stockmanagementhub.model.dto.ErrorResponse.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("/v2/users")
    public ResponseEntity<List<User>> getAllUser2(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") int pageSize) {

        Page<User> users = userService.getAllUser2(pageNo, pageSize);
        return ResponseEntity.ok(users.getContent());
    }
    @GetMapping("/v3/users")
    public ResponseEntity<List<User>> getAllUsers3(Pageable pageable){
        Page<User> users = userService.getAllUsers3(pageable);
        return ResponseEntity.ok(users.getContent());
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUserByFilter(
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size) {
        Page<User> users = userService.searchUserByFilter(firstName, userName, password, page, size);
        return ResponseEntity.ok(users.getContent());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id) {
        log.info("Get user id by {}", id);
        if (id < 1) {
            throw new IllegalArgumentException("User ID cannot be less than 1, please input correct ID");
        }
        return userService.getUser(id)
                          .map(user -> ResponseEntity.ok().body(user))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info("Request to create user => {}", user);
        if (user.getId() != null) {
            return ResponseEntity.badRequest()
                                 .body(ErrorResponse.buildErrorResponse(
                                         HttpStatus.BAD_REQUEST.value(),
                                         "Invalid User ID",
                                         "ID should be null, Id = " + user.getId(),
                                         List.of("ID should be null")
                                 ));
        }
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            return ResponseEntity.badRequest()
                                 .body(ErrorResponse.buildErrorResponse(
                                         HttpStatus.BAD_REQUEST.value(),
                                         "Invalid User ID",
                                         "ID cannot be null, Id = " + user.getId(),
                                         List.of("ID cannot be null")
                                 ));
        }
        Optional<User> updatedUser = userService.UpdateUser(user);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.badRequest().body(ErrorResponse.buildErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "User Not Found",
                    "User with id " + user.getId() + " doesn't exist, Input correct User ID",
                    List.of("User with id " + user.getId() + " doesn't exist")
            ));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
