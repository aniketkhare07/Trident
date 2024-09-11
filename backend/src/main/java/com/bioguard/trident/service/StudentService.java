package com.bioguard.trident.service;

import com.bioguard.trident.dto.StudentDto;
import com.bioguard.trident.exception.TridentException;

public interface StudentService {
    StudentDto registerStudent (StudentDto studentDto) throws TridentException;
}
