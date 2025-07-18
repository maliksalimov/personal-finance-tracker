package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


    List<User> findIsEnabledIsTrue();
    List<User> findIsEnabledIsFalse();


    List<User> findUserByCreatedAtBetween(java.time.LocalDateTime from, java.time.LocalDateTime to);

    List<User> findActiveUsersCreatedAfter(@Param("date") java.time.LocalDateTime from);

    Long countUsersByTwoFactorEnabledIsTrue();

    Page<User> findByIsEnabledTrue(Pageable pageable);

}
