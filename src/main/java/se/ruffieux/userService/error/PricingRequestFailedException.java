package se.ruffieux.userService.error;

public class PricingRequestFailedException extends Exception {
    public PricingRequestFailedException(String message) {
        super(message);
    }
}
