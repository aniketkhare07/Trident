package com.bioguard.trident.repository;

import com.bioguard.trident.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByAadharNumberAndBatchBatchCode(String aadharNumber, String batchCode);
    Optional<List<Student>> findByBatchBatchCode(String batchCode);
}
