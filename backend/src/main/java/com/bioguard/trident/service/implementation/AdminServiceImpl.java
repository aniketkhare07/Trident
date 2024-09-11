package com.bioguard.trident.service.implementation;

import com.bioguard.trident.dto.*;
import com.bioguard.trident.entity.*;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.repository.*;
import com.bioguard.trident.service.AdminService;
import com.bioguard.trident.utility.Convertor;
import com.bioguard.trident.utility.Status;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BiometricsRepository biometricsRepository;

    @Autowired
    private CenterRequestRepository centerRequestRepository;

    @Autowired
    private BatchRequestRepository batchRequestRepository;

//    User Region
    public UserDto registerUser (@Valid UserDto userDto) throws TridentException {
        Optional<UserDetails>optional = userRepository.findByUsername(userDto.getUsername());

        if(optional.isPresent()) {
            throw new TridentException("User is already registered");
        }
        String password = userDto.getPassword();
        User user = Convertor.convertToUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsActive(true);
        // Set the role to 'USER'
        user.setRole(Role.USER);
        user = userRepository.save(user);

        user.setPassword(password);

        return Convertor.convertToUserDto(user);
    }

    public List<UserDto> fetchUsers() throws TridentException {
        Optional<List<User>> optional = userRepository.findByRole(Role.USER);
        List<User> userList = optional.orElseThrow(() -> new TridentException("No users registered"));

        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach(user -> userDtoList.add(Convertor.convertToUserDto(user)));

        return userDtoList;
    }

    public UserDto setUserStatus (UserDto userDto) throws TridentException {
        Optional<UserDetails> optional = userRepository.findByUsername(userDto.getUsername());
        User user;
        if (optional.isPresent()) {
            UserDetails userDetails = optional.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        user.setIsActive(userDto.isActive());
        user = userRepository.save(user);

        return Convertor.convertToUserDto(user);
    }

//    Center Region
    public List<CenterRequestDto> centerRequests (CenterRequestDto centerRequestDto) throws TridentException {
        Optional<List<CenterRequest>> optionalCenterRequests = centerRequestRepository.findByStatus(centerRequestDto.getStatus());
        List<CenterRequest> centerRequestsList = new ArrayList<CenterRequest>();
        if(centerRequestDto.getStatus() == Status.Pending) {
            centerRequestsList = optionalCenterRequests.orElseThrow(() -> new TridentException("No pending request"));
        }
        else if(centerRequestDto.getStatus() == Status.Rejected) {
            centerRequestsList = optionalCenterRequests.orElseThrow(() -> new TridentException("No Rejected request"));
        }
        else if(centerRequestDto.getStatus() == Status.Approved) {
            centerRequestsList = optionalCenterRequests.orElseThrow(() -> new TridentException("No Approved request"));
        }

        List<CenterRequestDto> centerRequestDtos = new ArrayList<CenterRequestDto>();
        centerRequestsList.forEach(request -> centerRequestDtos.add(Convertor.convertToCenterRequestDto(request)));
        return centerRequestDtos;
    }

    public CenterRequestDto rejectCenter (CenterRequestDto centerRequestDto) throws TridentException {

        Optional<CenterRequest> optionalRequest = centerRequestRepository.findByCenterCodeAndUsername(centerRequestDto.getCenterCode(), centerRequestDto.getUsername());
        CenterRequest centerRequest = optionalRequest.orElseThrow(() -> new TridentException("No such request present"));

        Optional<UserDetails> optionalUserDetails = userRepository.findByUsername(centerRequestDto.getUsername());
        User user;
        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        centerRequest.setStatus(Status.Rejected);
        centerRequest = centerRequestRepository.save(centerRequest);

        return Convertor.convertToCenterRequestDto(centerRequest);
    }

    public String removeCenterRequest (CenterRequestDto centerRequestDto) throws TridentException {

        Optional<CenterRequest> optionalRequest = centerRequestRepository.findByCenterCodeAndUsername(centerRequestDto.getCenterCode(), centerRequestDto.getUsername());
        CenterRequest centerRequest = optionalRequest.orElseThrow(() -> new TridentException("No such request present"));

        Optional<UserDetails> optionalUserDetails = userRepository.findByUsername(centerRequestDto.getUsername());
        User user;
        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        if(centerRequest.getStatus() == Status.Rejected){
            centerRequestRepository.delete(centerRequest);
            return "Center Request removed Successfully";
        }else {
            throw new TridentException("Request can not be removed");
        }
    }

    public CenterDto registerCenter (CenterRequestDto centerRequestDto) throws TridentException {

        Optional<CenterRequest> optionalRequest = centerRequestRepository.findByCenterCodeAndUsername(centerRequestDto.getCenterCode(), centerRequestDto.getUsername());
        CenterRequest centerRequest = optionalRequest.orElseThrow(() -> new TridentException("No such request present"));

        Optional<UserDetails> optionalUserDetails = userRepository.findByUsername(centerRequestDto.getUsername());
        User user;
        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }
        Optional<Center> optional = centerRepository.findByCenterCodeAndUserUsername(centerRequestDto.getCenterCode(), centerRequestDto.getUsername());
        if (optional.isPresent()) {
            throw new TridentException("Center is already registered");
        }
        Center center = new Center();
        center.setCenterCode(centerRequestDto.getCenterCode());
        center.setUser(user);
        center = centerRepository.save(center);

        centerRequest.setStatus(Status.Approved);
        centerRequest = centerRequestRepository.save(centerRequest);

        return Convertor.convertToCenterDto(center);
    }

    public List<CenterDto> fetchCenters(String username) throws TridentException {
        Optional<List<Center>> optional = centerRepository.findByUserUsername(username);
        List<Center> centerList = optional.orElseThrow(() -> new TridentException("No center registered with given user"));

        List<CenterDto> centerDtoList = new ArrayList<>();
        centerList.forEach(center -> centerDtoList.add(Convertor.convertToCenterDto(center)));

        return centerDtoList;
    }

    public List<CenterDto> fetchAllCenters () throws TridentException {
        Iterable<Center> optional = centerRepository.findAll();

        List<Center> centerList = new ArrayList<Center>();
        optional.forEach(centerList::add);
        if(centerList.isEmpty()) {
            throw new TridentException("No center registered yet.");
        }

        List<CenterDto> centerDtoList = new ArrayList<>();
        centerList.forEach(center -> centerDtoList.add(Convertor.convertToCenterDto(center)));

        return centerDtoList;
    }

//    Batch Region
    public List<BatchRequestDto> batchRequests (BatchRequestDto batchRequestDto) throws TridentException {
        Optional<List<BatchRequest>> optionalBatchRequests = batchRequestRepository.findByStatus(batchRequestDto.getStatus());
        List<BatchRequest> batchRequestsList = new ArrayList<BatchRequest>();
        if(batchRequestDto.getStatus() == Status.Pending) {
            batchRequestsList = optionalBatchRequests.orElseThrow(() -> new TridentException("No pending request"));
        }
        else if(batchRequestDto.getStatus() == Status.Rejected) {
            batchRequestsList = optionalBatchRequests.orElseThrow(() -> new TridentException("No Rejected request"));
        }
        else if(batchRequestDto.getStatus() == Status.Approved) {
            batchRequestsList = optionalBatchRequests.orElseThrow(() -> new TridentException("No Approved request"));
        }

        List<BatchRequestDto> batchRequestDtos = new ArrayList<BatchRequestDto>();
        batchRequestsList.forEach(request -> batchRequestDtos.add(Convertor.convertToBatchRequestDto(request)));
        return batchRequestDtos;
    }

    public BatchRequestDto rejectBatch (BatchRequestDto batchRequestDto) throws TridentException {
        Optional<BatchRequest> optionalRequest = batchRequestRepository.findByBatchCodeAndCenterCode(batchRequestDto.getBatchCode(), batchRequestDto.getCenterCode());
        BatchRequest batchRequest = optionalRequest.orElseThrow(() -> new TridentException("No such request present"));

        Optional<UserDetails> optionalUserDetails = userRepository.findByUsername(batchRequestDto.getUsername());
        User user;
        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        Optional<Center> optionalCenter = centerRepository.findByCenterCodeAndUserUsername(batchRequestDto.getCenterCode(), batchRequestDto.getUsername());
        Center center = optionalCenter.orElseThrow(() -> new TridentException("Center not found"));

        batchRequest.setStatus(Status.Rejected);
        batchRequest = batchRequestRepository.save(batchRequest);

        return Convertor.convertToBatchRequestDto(batchRequest);
    }

    public String removeBatchRequest (BatchRequestDto batchRequestDto) throws TridentException {
        Optional<BatchRequest> optionalRequest = batchRequestRepository.findByBatchCodeAndCenterCode(batchRequestDto.getBatchCode(), batchRequestDto.getCenterCode());
        BatchRequest batchRequest = optionalRequest.orElseThrow(() -> new TridentException("No such request present"));

        Optional<UserDetails> optionalUserDetails = userRepository.findByUsername(batchRequestDto.getUsername());
        User user;
        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        if(batchRequest.getStatus() == Status.Rejected){
            batchRequestRepository.delete(batchRequest);
            return "Batch Request removed Successfully";
        }else {
            throw new TridentException("Request can not be removed");
        }
    }

    public BatchDto createBatch (BatchRequestDto batchRequestDto) throws TridentException {

        Optional<BatchRequest> optionalRequest = batchRequestRepository.findByBatchCodeAndCenterCode(batchRequestDto.getBatchCode(), batchRequestDto.getCenterCode());
        BatchRequest batchRequest = optionalRequest.orElseThrow(() -> new TridentException("No such request present"));

        Optional<UserDetails> optionalUserDetails = userRepository.findByUsername(batchRequestDto.getUsername());
        User user;
        if (optionalUserDetails.isPresent()) {
            UserDetails userDetails = optionalUserDetails.get();
            if (userDetails instanceof User) {
                user = (User) userDetails;
            } else {
                throw new TridentException("User not found");
            }
        } else {
            throw new TridentException("User not found");
        }

        Optional<Center> optionalCenter = centerRepository.findByCenterCodeAndUserUsername(batchRequestDto.getCenterCode(), batchRequestDto.getUsername());
        Center center = optionalCenter.orElseThrow(() -> new TridentException("Center not found"));

        Batch batch = new Batch();
        batch.setBatchCode(batchRequestDto.getBatchCode());
        batch.setCenter(center);

        batch = batchRepository.save(batch);

        batchRequest.setStatus(Status.Approved);
        batchRequest = batchRequestRepository.save(batchRequest);

        return Convertor.convertToBatchDto(batch);
    }

    public List<BatchDto> fetchBatches(String centerCode) throws TridentException {
        Optional<List<Batch>> optional = batchRepository.findByCenterCenterCode(centerCode);
        List<Batch> batchList = optional.orElseThrow(() -> new TridentException("No batch registered in given center"));

        List<BatchDto> bacthDtoList = new ArrayList<>();
        batchList.forEach(batch-> bacthDtoList.add(Convertor.convertToBatchDto(batch)));

        return bacthDtoList;
    }

    public List<BatchDto> fetchAllBatches () throws TridentException {
        Iterable<Batch> optional = batchRepository.findAll();
        List<Batch> batchList = new ArrayList<Batch>();
        optional.forEach(batchList::add);
        if(batchList.isEmpty()) {
            throw new TridentException("No center registered yet.");
        }
        List<BatchDto> bacthDtoList = new ArrayList<>();
        batchList.forEach(batch-> bacthDtoList.add(Convertor.convertToBatchDto(batch)));

        return bacthDtoList;
    }

//    Student Region
    public List<StudentDto> fetchAllStudents () throws TridentException {
        Iterable<Student> optionalStudents = studentRepository.findAll();
        List<Student> studentsList = new ArrayList<Student>();
        optionalStudents.forEach(studentsList::add);
        if(studentsList.isEmpty()) {
            throw new TridentException("No center registered yet.");
        }

        List<StudentDto> studentDtos = new ArrayList<StudentDto>();
        studentsList.forEach(student -> studentDtos.add(Convertor.convertToStudentDto(student)));
        return studentDtos;
    }

    public List<StudentDto> fetchStudents (String batchCode) throws TridentException {
        Optional<Batch> optionalBatch = batchRepository.findByBatchCode(batchCode);
        Batch batch = optionalBatch.orElseThrow(() -> new TridentException("Batch Not Found"));

        Optional<List<Student>> optionalStudents = studentRepository.findByBatchBatchCode(batch.getBatchCode());
        List<Student> students = optionalStudents.orElseThrow(() -> new TridentException("No student registered in this batch"));

        List<StudentDto> studentDtos = new ArrayList<StudentDto>();
        students.forEach(student -> studentDtos.add(Convertor.convertToStudentDto(student)));
        return studentDtos;
    }

    public BiometricsDto fetchBiometrics (Long studentId) throws TridentException {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseThrow(() -> new TridentException("Student not found"));

        Optional<Biometrics> optionalBiometrics = biometricsRepository.findByStudentStudentid(studentId);
        Biometrics biometrics = optionalBiometrics.orElseThrow(() -> new TridentException("Biometric details not found for student"));

        return Convertor.convertToBiometricsDto(biometrics);
    }


}
