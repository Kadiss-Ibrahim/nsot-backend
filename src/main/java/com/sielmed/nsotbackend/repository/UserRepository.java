package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE " +
            "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%',CAST(:username AS string), '%'))) AND " +
            "(:role IS NULL OR u.role = :role)")
    List<User> search(@Param("username") String username, @Param("role") com.sielmed.nsotbackend.enums.Role role);
}
