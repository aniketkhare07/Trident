package com.bioguard.trident.service.implementation;

import com.bioguard.trident.dto.StudentDto;
import com.bioguard.trident.entity.*;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.repository.*;
import com.bioguard.trident.service.StudentService;
import com.bioguard.trident.utility.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BiometricsRepository biometricsRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private UserRepository userRepository;

    public StudentDto registerStudent (StudentDto studentDto) throws TridentException {
        Optional<Batch> optionalBatch = batchRepository.findByBatchCode(studentDto.getBatchDto().getBatchCode());
        Batch batch = optionalBatch.orElseThrow(() -> new TridentException("No Batch Found"));

        Optional<Student> optionalStudent = studentRepository.findByAadharNumberAndBatchBatchCode(studentDto.getAadharNumber(), studentDto.getBatchDto().getBatchCode());
        if (optionalStudent.isPresent()) {
            throw new TridentException("Student already exists");
        }

        studentDto.setBatchDto(Convertor.convertToBatchDto(batch));

        Student student = Convertor.convertToStudentEntity(studentDto);
        student = studentRepository.save(student);

        Biometrics biometrics = Convertor.convertToBiometricsEntity(studentDto.getBiometricsDto());
        biometrics.setStudent(student);
        biometrics = biometricsRepository.save(biometrics);

        student.setBiometrics(biometrics);
        student = studentRepository.save(student);

        return Convertor.convertToStudentDto(student);
    }
}
