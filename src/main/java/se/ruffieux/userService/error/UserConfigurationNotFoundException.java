package se.ruffieux.userService.error;

public class UserConfigurationNotFoundException extends Exception {
    public UserConfigurationNotFoundException(String message) {
        super(message);
    }
}
