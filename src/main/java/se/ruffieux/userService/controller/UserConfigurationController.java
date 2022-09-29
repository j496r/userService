package se.ruffieux.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.ruffieux.userService.entity.UserConfiguration;
import se.ruffieux.userService.service.UserConfigurationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserConfigurationController {

    @Autowired
    private UserConfigurationService userService;

    @PutMapping("/users/{id}")
    UserConfiguration handleUserConfigurationUpdate(@PathVariable("id") Long userId,
            @RequestBody UserConfiguration user) throws Exception {
        UserConfiguration update = userService.updateUser(userId, user);
        return update;
    }

    @GetMapping("/users/{id}")
    UserConfiguration handleGetUserConfiguration(@RequestParam String param) {
        // TODO: send user data back (really there should be a check that it is the
        // correct user)
        return null;
    }

}
