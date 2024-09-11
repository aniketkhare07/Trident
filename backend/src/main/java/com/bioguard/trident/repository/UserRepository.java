package com.bioguard.trident.repository;

import com.bioguard.trident.entity.Role;
import com.bioguard.trident.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<UserDetails> findByUsername(String username);
    Optional<List<User>> findByRole(Role role);
}
