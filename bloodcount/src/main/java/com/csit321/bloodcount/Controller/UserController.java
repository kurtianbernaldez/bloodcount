package com.csit321.bloodcount.Controller;

import com.csit321.bloodcount.Entity.UserEntity;
import com.csit321.bloodcount.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/insertUser")
    public UserEntity insertUser(@RequestBody UserEntity user) {
        return userService.insertUser(user);
    }

    @GetMapping("/getAllUsers")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/updateUser")
    public UserEntity updateUser(@RequestParam int userId, @RequestBody UserEntity newUserDetails) {
        return userService.updateUser(userId, newUserDetails);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }
}
