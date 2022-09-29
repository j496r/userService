package se.ruffieux.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.ruffieux.userService.entity.UserConfiguration;
import se.ruffieux.userService.service.UserConfigurationService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserConfigurationController {

    @Autowired
    private UserConfigurationService userConfigurationService;

    @PutMapping("my-email-api/user-configurations/{id}")
    UserConfiguration handleUserConfigurationUpdate(@PathVariable("id") Long userId,
            @RequestBody UserConfiguration user) throws Exception {
        UserConfiguration update = userConfigurationService.updateUserConfiguration(userId, user);
        return update;
    }

    @GetMapping("support-tool-api/user-configurations/{id}")
    UserConfiguration handleGetUserConfiguration(@PathVariable("id") Long userId) throws Exception {
        UserConfiguration userConfig = userConfigurationService.getUserConfiguration(userId);
        return userConfig;
    }

}
