package se.ruffieux.userService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.ruffieux.userService.entity.HttpPricingResponse;
import se.ruffieux.userService.entity.UserConfiguration;
import se.ruffieux.userService.error.PricingRequestFailedException;
import se.ruffieux.userService.error.UserConfigurationNotFoundException;
import se.ruffieux.userService.repository.UserConfigurationRepository;

@Service
public class UserConfigurationServiceImpl implements UserConfigurationService {
    @Autowired
    private UserConfigurationRepository userRepository;

    @Override
    @Transactional
    public UserConfiguration updateUserConfiguration(Long userId, UserConfiguration newData)
            throws UserConfigurationNotFoundException, Exception {

        // Get new price
        final HttpPricingResponse response = sendGetPriceRequestToPricingService(newData.getEmailQuotaInGB());
        if (response.statusCode() != 200) {
            throw new PricingRequestFailedException("Failed to retreave pricing for the specified quota");
        }

        final int costForNewQuota = response.body().getTotalCostInCents();

        // Store to database
        Optional<UserConfiguration> userInDb = userRepository.findById(userId);
        if (!userInDb.isPresent()) {
            throw new UserConfigurationNotFoundException("User was not found");
        }

        UserConfiguration user = userInDb.get();
        user.setEmailQuotaInGB(newData.getEmailQuotaInGB());
        user.setTotalCostInCents(costForNewQuota);

        // Update email platform
        int statusCode = sentUpdatedQuotaToEmailPlatform(user.getId(),
                user.getEmailQuotaInGB());
        if (statusCode != 200) {
            // TODO: Create specific exceptions for this case to provide better replies.
            // TODO: Roll back database change if we fail to update quota on email platfrom
            throw new Exception("Failed to update quota on email platform.");
        }

        return userRepository.save(user);
    }

    @Override
    public UserConfiguration getUserConfiguration(Long userId) throws UserConfigurationNotFoundException {
        Optional<UserConfiguration> userInDb = userRepository.findById(userId);
        if (!userInDb.isPresent()) {
            throw new UserConfigurationNotFoundException("User was not found");
        }
        return userInDb.get();
    }

    // Dummy code for talking with other services
    @Autowired
    private HttpPricingResponse dummyPricingResponse;

    private HttpPricingResponse sendGetPriceRequestToPricingService(int quota) {
        return dummyPricingResponse;
    };

    @Value("${emailplatform.response.status: 200}")
    private int dummyEmailPlatformResponse;

    private int sentUpdatedQuotaToEmailPlatform(Long userId, int quota) {
        return dummyEmailPlatformResponse;
    }

}
