package com.bioguard.trident.utility;

import com.bioguard.trident.dto.*;
import com.bioguard.trident.dto.CenterRequestDto;
import com.bioguard.trident.entity.*;

import java.util.HashSet;
import java.util.Set;

public class Convertor {

    public static User convertToUserEntity(UserDto userDto) {
        User user = new User();
        user.setUserid(userDto.getUserid());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setContact(userDto.getContact());
        user.setIsActive(userDto.isActive());
        user.setRole(userDto.getRole());

        // Conversion for centers
        Set<Center> centers = new HashSet<>();
        if (userDto.getCenterDtos() != null) {
            for (CenterDto centerDto : userDto.getCenterDtos()) {
                centers.add(convertToCenterEntity(centerDto));
            }
        }
        user.setCenters(centers);

        return user;
    }

    public static UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserid(user.getUserid());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setContact(user.getContact());
        userDto.setActive(user.getIsActive());
        userDto.setRole(user.getRole());

        // Conversion for centerDtos
        Set<CenterDto> centerDtos = new HashSet<>();
        if (user.getCenters() != null) {
            for (Center center : user.getCenters()) {
                centerDtos.add(convertToCenterDto(center));
            }
        }
        userDto.setCenterDtos(centerDtos);

        return userDto;
    }

    public static Center convertToCenterEntity(CenterDto centerDto) {
        Center center = new Center();
        center.setCenterid(centerDto.getCenterid());
        center.setCenterCode(centerDto.getCenterCode());
        center.setUser(convertToUserEntity(centerDto.getUserDto()));

        // Conversion for batches
        Set<Batch> batches = new HashSet<>();
        if (centerDto.getBatcheDtos() != null) {
            for (BatchDto batchDto : centerDto.getBatcheDtos()) {
                batches.add(convertToBatchEntity(batchDto));
            }
        }
        center.setBatches(batches);

        return center;
    }

    public static CenterDto convertToCenterDto(Center center) {
        CenterDto centerDto = new CenterDto();
        centerDto.setCenterid(center.getCenterid());
        centerDto.setCenterCode(center.getCenterCode());
        centerDto.setUserDto(convertToUserDto(center.getUser()));

        // Conversion for batcheDtos
        Set<BatchDto> batcheDtos = new HashSet<>();
        if (center.getBatches() != null) {
            for (Batch batch : center.getBatches()) {
                batcheDtos.add(convertToBatchDto(batch));
            }
        }
        centerDto.setBatcheDtos(batcheDtos);
        return centerDto;
    }

    public static Batch convertToBatchEntity(BatchDto batchDto) {
        Batch batch = new Batch();
        batch.setBatcheid(batchDto.getBatcheid());
        batch.setBatchCode(batchDto.getBatchCode());

        if (batchDto.getCenterDto() != null) {
            Center center = new Center();
            center.setCenterid(batchDto.getCenterDto().getCenterid());
            center.setCenterCode(batchDto.getCenterDto().getCenterCode());
            batch.setCenter(center);
        }

        return batch;
    }

    public static BatchDto convertToBatchDto(Batch batch) {
        BatchDto batchDto = new BatchDto();
        batchDto.setBatcheid(batch.getBatcheid());
        batchDto.setBatchCode(batch.getBatchCode());

        if (batch.getCenter() != null) {
            CenterDto centerDto = new CenterDto();
            centerDto.setCenterid(batch.getCenter().getCenterid());
            centerDto.setCenterCode(batch.getCenter().getCenterCode());
            batchDto.setCenterDto(centerDto);
        }

        return batchDto;
    }

    public static Student convertToStudentEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setStudentid(studentDto.getStudentid());
        student.setName(studentDto.getName());
        student.setAadharNumber(studentDto.getAadharNumber());

        if (studentDto.getBatchDto() != null) {
            Batch batch = new Batch();
            batch.setBatcheid(studentDto.getBatchDto().getBatcheid());
            batch.setBatchCode(studentDto.getBatchDto().getBatchCode());
            student.setBatch(batch);
        }

        return student;
    }

    public static StudentDto convertToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentid(student.getStudentid());
        studentDto.setName(student.getName());
        studentDto.setAadharNumber(student.getAadharNumber());

        if (student.getBatch() != null) {
            BatchDto batchDto = new BatchDto();
            batchDto.setBatcheid(student.getBatch().getBatcheid());
            batchDto.setBatchCode(student.getBatch().getBatchCode());
            studentDto.setBatchDto(batchDto);
        }

        return studentDto;
    }

    public static Biometrics convertToBiometricsEntity (BiometricsDto biometricsDto) {
        Biometrics biometrics = new Biometrics();
        biometrics.setBiometricid(biometricsDto.getBiometricid());
        biometrics.setFingerprint1(biometricsDto.getFingerprint1());
        biometrics.setFingerprint2(biometricsDto.getFingerprint2());
        biometrics.setFingerprint3(biometricsDto.getFingerprint3());
        biometrics.setFingerprint4(biometricsDto.getFingerprint4());
        biometrics.setFingerprint5(biometricsDto.getFingerprint5());
        biometrics.setIris(biometricsDto.getIris());

        return biometrics;
    }

    public static BiometricsDto convertToBiometricsDto (Biometrics biometrics) {
        BiometricsDto biometricsDto = new BiometricsDto();
        biometricsDto.setBiometricid(biometrics.getBiometricid());
        biometricsDto.setFingerprint1(biometrics.getFingerprint1());
        biometricsDto.setFingerprint2(biometrics.getFingerprint2());
        biometricsDto.setFingerprint3(biometrics.getFingerprint3());
        biometricsDto.setFingerprint4(biometrics.getFingerprint4());
        biometricsDto.setFingerprint5(biometrics.getFingerprint5());
        biometricsDto.setIris(biometrics.getIris());

        return biometricsDto;
    }

    public static CenterRequest convertToCenterRequestEntity(CenterRequestDto centerRequestDto) {
        CenterRequest centerRequest = new CenterRequest();

        centerRequest.setRequestId(centerRequestDto.getRequestId());
        centerRequest.setRequestDate(centerRequestDto.getRequestDate());
        centerRequest.setCenterCode(centerRequestDto.getCenterCode());
        centerRequest.setUsername(centerRequestDto.getUsername());
        centerRequest.setStatus(centerRequestDto.getStatus());

        return centerRequest;
    }

    public static CenterRequestDto convertToCenterRequestDto (CenterRequest centerRequest) {
        CenterRequestDto centerRequestDto = new CenterRequestDto();

        centerRequestDto.setRequestId(centerRequest.getRequestId());
        centerRequestDto.setRequestDate(centerRequest.getRequestDate());
        centerRequestDto.setCenterCode(centerRequest.getCenterCode());
        centerRequestDto.setUsername(centerRequest.getUsername());
        centerRequestDto.setStatus(centerRequest.getStatus());

        return centerRequestDto;
    }

    public static BatchRequest convertToBatchRequestEntity (BatchRequestDto batchRequestDto) {
        BatchRequest batchRequest = new BatchRequest();

        batchRequest.setRequestId(batchRequestDto.getRequestId());
        batchRequest.setRequestDate(batchRequestDto.getRequestDate());
        batchRequest.setUsername(batchRequestDto.getUsername());
        batchRequest.setCenterCode(batchRequestDto.getCenterCode());
        batchRequest.setBatchCode(batchRequestDto.getBatchCode());
        batchRequest.setStatus(batchRequestDto.getStatus());

        return batchRequest;
    }

    public static BatchRequestDto convertToBatchRequestDto (BatchRequest batchRequest) {
        BatchRequestDto batchRequestDto = new BatchRequestDto();

        batchRequestDto.setRequestId(batchRequest.getRequestId());
        batchRequestDto.setRequestDate(batchRequest.getRequestDate());
        batchRequestDto.setUsername(batchRequest.getUsername());
        batchRequestDto.setCenterCode(batchRequest.getCenterCode());
        batchRequestDto.setBatchCode(batchRequest.getBatchCode());
        batchRequestDto.setStatus(batchRequest.getStatus());
        return batchRequestDto;
    }
}
