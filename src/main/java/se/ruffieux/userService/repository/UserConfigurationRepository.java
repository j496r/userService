package se.ruffieux.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.ruffieux.userService.entity.UserConfiguration;

@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Long> {

    // public User findByUserId(Long id);

}
