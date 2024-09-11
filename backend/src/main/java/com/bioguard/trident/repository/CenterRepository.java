package com.bioguard.trident.repository;

import com.bioguard.trident.entity.Center;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends CrudRepository<Center, Long> {
    Optional<Center> findByCenterCodeAndUserUsername(String centerCode, String username);
    Optional<List<Center>> findByUserUsername(String username);
}
