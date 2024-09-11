package com.bioguard.trident.repository;

import com.bioguard.trident.entity.BatchRequest;
import com.bioguard.trident.entity.CenterRequest;
import com.bioguard.trident.utility.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BatchRequestRepository extends CrudRepository<BatchRequest, Long> {
    Optional<BatchRequest> findByBatchCodeAndCenterCode(String batchCode, String centerCode);
    Optional<List<BatchRequest>> findByStatus(Status status);
}
