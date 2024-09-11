package com.bioguard.trident.repository;

import com.bioguard.trident.entity.Biometrics;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BiometricsRepository extends CrudRepository<Biometrics, Long> {
    Optional<Biometrics> findByStudentStudentid (Long studentId);
}
