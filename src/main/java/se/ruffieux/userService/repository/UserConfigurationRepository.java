package se.ruffieux.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.ruffieux.userService.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // public User findByUserId(Long id);

}
