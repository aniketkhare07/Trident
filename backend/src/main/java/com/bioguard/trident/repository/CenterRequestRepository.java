package com.bioguard.trident.repository;

import com.bioguard.trident.entity.CenterRequest;
import com.bioguard.trident.utility.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CenterRequestRepository extends CrudRepository<CenterRequest,Long> {
    Optional<CenterRequest> findByCenterCodeAndUsername(String centerCode, String username);
    Optional<List<CenterRequest>> findByStatus(Status status);
}
