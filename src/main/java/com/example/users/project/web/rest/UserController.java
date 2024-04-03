package com.example.users.project.web.rest;

import com.example.users.project.domain.Users;
import com.example.users.project.dto.RequestDto;
import com.example.users.project.service.UserService;
import com.example.users.project.service.UserSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserSpecifications<Users> usersUserSpecifications;

    public UserController(UserService userService, UserSpecifications<Users> usersUserSpecifications) {
        this.userService = userService;
        this.usersUserSpecifications = usersUserSpecifications;
    }

    @PostMapping("/register")
    public ResponseEntity creatUser(Users users) {
        if (!checkPasswordLength(users.getPassword())) {
            return new ResponseEntity("Password length is less than 4", HttpStatus.BAD_REQUEST);
        }
        if (userService.checkUserName(users.getUserName())) {
            return new ResponseEntity("This user has already registered", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.save(users));
    }

    private Boolean checkPasswordLength(String password) {
        return password.length() >= 4;

    }

    @RequestMapping("/users")
    public ResponseEntity create(@RequestBody Users user) {
        Users user1 = userService.save(user);
        return ResponseEntity.ok(user1);
    }

    @PutMapping("/users")
    public ResponseEntity update(@RequestBody Users user) {
        Users user1 = userService.save(user);
        return ResponseEntity.ok(user1);
    }

    @DeleteMapping("/users")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully ");
    }

    @GetMapping("/users")
    public ResponseEntity getAll() {
        List<Users> usersList = userService.findAll();
        return ResponseEntity.ok(usersList);
    }

    @PostMapping("/users/search")
    public List<Users> getUsers(@RequestBody RequestDto requestDto) {
        Specification<Users> usersList = usersUserSpecifications.getSearchSpecification(requestDto.getSearchRequestDto());
        return userService.findAll(usersList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        Users usersList = userService.getUserById(id);
        return ResponseEntity.ok(usersList);
    }


}