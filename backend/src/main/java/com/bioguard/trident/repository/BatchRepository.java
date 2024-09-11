package com.bioguard.trident.repository;

import com.bioguard.trident.entity.Batch;
import com.bioguard.trident.entity.Center;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BatchRepository extends CrudRepository<Batch, Long> {
    Optional<List<Batch>> findByCenterCenterCode(String centerCode);
    Optional<Batch> findByBatchCode (String batchCode);
}
