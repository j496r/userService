package se.ruffieux.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.ruffieux.userService.entity.User;
import se.ruffieux.userService.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/users/{id}/")
    User handleUserUpdate(@PathVariable("id") Long userId, @RequestBody User user) throws Exception {
        User update = userService.updateUser(userId, user);
        return update;
    }

}
