package se.ruffieux.userService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.ruffieux.userService.entity.HttpPricingResponse;
import se.ruffieux.userService.entity.UserConfiguration;
import se.ruffieux.userService.error.PricingRequestFailedException;
import se.ruffieux.userService.error.UserConfigurationNotFoundException;
import se.ruffieux.userService.repository.UserConfigurationRepository;
import se.ruffieux.userService.service.UserConfigurationService;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private UserConfigurationService userConfigurationService;

	@Autowired
	private HttpPricingResponse pricingResponse;

	@MockBean
	private UserConfigurationRepository mockRepo;

	@BeforeEach
	void setup() {
		UserConfiguration userConfiguration = new UserConfiguration();
		userConfiguration.setId(1L);
		userConfiguration.setEmailQuotaInGB(1);
		userConfiguration.setTotalCostInCents(0);

		Mockito.when(mockRepo.findById(1L))
				.thenReturn(Optional.of(userConfiguration));

		Mockito.when(mockRepo.save(Mockito.any(UserConfiguration.class)))
				.thenAnswer(i -> i.getArguments()[0]);
	}

	@Test
	void updateQuotaService_happyPath() throws UserConfigurationNotFoundException, Exception {
		UserConfiguration newConfig = new UserConfiguration();
		newConfig.setEmailQuotaInGB(100);
		UserConfiguration reply = userConfigurationService.updateUserConfiguration(1L, newConfig);
		assertEquals(reply.getEmailQuotaInGB(), 100);
	}

	@Test
	void updateQuotaService_failPriceFetching() {
		pricingResponse.status = 400;
		UserConfiguration newConfig = new UserConfiguration();
		newConfig.setEmailQuotaInGB(100);
		try {
			userConfigurationService.updateUserConfiguration(1L, newConfig);
			fail("Pricing request failed, an exception should have been thrown");
		} catch (PricingRequestFailedException e) {
			// We are expected to end up here
		} catch (Exception e) {
			fail("Unexpected Exception type");
		} finally {
			pricingResponse.status = 200;
		}
	}

	/*
	 * TODO: Continue with this testcase if I have time
	 * 
	 * @Test
	 * void updateQuotaService_failToUpdateEmailPlatformQuotaValue() throws
	 * Exception {
	 * UserConfiguration newConfig = new UserConfiguration();
	 * newConfig.setEmailQuotaInGB(100);
	 * try {
	 * UserConfiguration reply = userConfigurationService.updateUser(1L, newConfig);
	 * }
	 * catch {
	 * 
	 * }
	 * assertEquals(reply.getEmailQuotaInGB(), 1);
	 * }
	 */

}
