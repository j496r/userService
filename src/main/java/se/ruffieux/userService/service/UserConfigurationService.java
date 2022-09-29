package se.ruffieux.userService.service;

import se.ruffieux.userService.entity.UserConfiguration;
import se.ruffieux.userService.error.UserConfigurationNotFoundException;

public interface UserConfigurationService {
    public UserConfiguration updateUser(Long userId, UserConfiguration user)
            throws UserConfigurationNotFoundException, Exception;
}
