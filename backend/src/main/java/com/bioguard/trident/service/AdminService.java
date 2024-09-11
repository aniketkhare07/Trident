package com.bioguard.trident.service;

import com.bioguard.trident.dto.*;
import com.bioguard.trident.entity.Center;
import com.bioguard.trident.exception.TridentException;

import javax.swing.plaf.synth.Region;
import java.util.List;

public interface AdminService {

//    User Region
    UserDto registerUser(UserDto userDto) throws TridentException;
    List<UserDto> fetchUsers() throws TridentException;
    UserDto setUserStatus (UserDto userDto) throws TridentException;

//    Center Region
    List<CenterRequestDto> centerRequests (CenterRequestDto centerRequestDto) throws TridentException;
    CenterRequestDto rejectCenter (CenterRequestDto centerRequestDto) throws TridentException;
    String removeCenterRequest (CenterRequestDto centerRequestDto) throws TridentException;
    CenterDto registerCenter (CenterRequestDto centerRequestDto) throws TridentException;
    List<CenterDto> fetchCenters (String username) throws TridentException;
    List<CenterDto> fetchAllCenters () throws TridentException;

//    Batch Region
    List<BatchRequestDto> batchRequests (BatchRequestDto batchRequestDto) throws TridentException;
    BatchRequestDto rejectBatch (BatchRequestDto batchRequestDto) throws TridentException;
    String removeBatchRequest (BatchRequestDto batchRequestDto) throws TridentException;
    BatchDto createBatch (BatchRequestDto batchRequestDto) throws TridentException;
    List<BatchDto> fetchBatches (String centerCode) throws TridentException;
    List<BatchDto> fetchAllBatches () throws TridentException;

//    Student Region
    List<StudentDto> fetchStudents (String batchCode) throws TridentException;
    BiometricsDto fetchBiometrics (Long studentId) throws TridentException;
    List<StudentDto> fetchAllStudents () throws TridentException;
}
