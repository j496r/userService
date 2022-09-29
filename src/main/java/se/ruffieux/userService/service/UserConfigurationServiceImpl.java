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
    public UserConfiguration updateUserConfiguration(Long userId, UserConfiguration newConfig)
            throws UserConfigurationNotFoundException, Exception {

        // Get new price
        final HttpPricingResponse response = sendGetPriceRequestToPricingService(newConfig.getEmailQuotaInGB());
        if (response.statusCode() != 200) {
            throw new PricingRequestFailedException("Failed to retreave pricing for the specified quota");
        }

        final int costForNewQuota = response.body().getTotalCostInCents();

        // Store to database
        Optional<UserConfiguration> userConfigInDb = userRepository.findById(userId);
        if (!userConfigInDb.isPresent()) {
            throw new UserConfigurationNotFoundException("User was not found");
        }

        UserConfiguration userConfig = userConfigInDb.get();
        final int oldQuota = userConfig.getEmailQuotaInGB();
        final int oldCost = userConfig.getTotalCostInCents();
        userConfig.setEmailQuotaInGB(newConfig.getEmailQuotaInGB());
        userConfig.setTotalCostInCents(costForNewQuota);

        UserConfiguration updatedConfig = userRepository.save(userConfig);

        // Update email platform
        int statusCode = sentUpdatedQuotaToEmailPlatform(updatedConfig.getId(),
                updatedConfig.getEmailQuotaInGB());
        if (statusCode != 200) {
            updatedConfig.setEmailQuotaInGB(oldQuota);
            updatedConfig.setTotalCostInCents(oldCost);
            userRepository.save(updatedConfig);
            throw new Exception("Failed to update quota on email platform.");
        }

        return updatedConfig;
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
