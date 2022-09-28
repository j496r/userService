package se.ruffieux.userService.service;

import se.ruffieux.userService.entity.User;
import se.ruffieux.userService.error.UserNotFoundException;

public interface UserService {
    public User updateUser(Long userId, User user) throws UserNotFoundException;
}
