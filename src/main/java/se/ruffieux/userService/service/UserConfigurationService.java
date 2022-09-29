package se.ruffieux.userService.service;

import se.ruffieux.userService.entity.UserConfiguration;
import se.ruffieux.userService.error.UserConfigurationNotFoundException;

public interface UserConfigurationService {
    public UserConfiguration updateUserConfiguration(Long userId, UserConfiguration user)
            throws UserConfigurationNotFoundException, Exception;

    public UserConfiguration getUserConfiguration(Long userId) throws UserConfigurationNotFoundException;
}
