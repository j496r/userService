package se.ruffieux.userService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.ruffieux.userService.entity.User;
import se.ruffieux.userService.error.UserNotFoundException;
import se.ruffieux.userService.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User updateUser(Long userId, User newData) throws UserNotFoundException {
        Optional<User> userInDb = userRepository.findById(userId);
        if (!userInDb.isPresent()) {
            throw new UserNotFoundException("User was not found");
        }
        User user = userInDb.get();
        user.setEmailQuotaInGB(newData.getEmailQuotaInGB());
        return userRepository.save(user);
    }
}
