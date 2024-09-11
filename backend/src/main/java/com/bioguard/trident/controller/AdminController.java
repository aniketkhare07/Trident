package com.bioguard.trident.controller;

import com.bioguard.trident.dto.*;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi Admin");
    }

//    User Region
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) throws TridentException {
        return  ResponseEntity.ok(adminService.registerUser(userDto));
    }

    @GetMapping("/fetch-users")
    public ResponseEntity<List<UserDto>> fetchUsers() throws TridentException {
        return ResponseEntity.ok(adminService.fetchUsers());
    }

    @PostMapping("/set-status")
    public ResponseEntity<UserDto> setUserStatus(@RequestBody UserDto userDto) throws TridentException {
        return ResponseEntity.ok(adminService.setUserStatus(userDto));
    }

//    Center Region
    @PostMapping("/center-requests")
    public ResponseEntity<List<CenterRequestDto>> centerRequests(@RequestBody CenterRequestDto centerRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.centerRequests(centerRequestDto));
    }

    @PostMapping("/reject-center")
    public ResponseEntity<CenterRequestDto> rejectCenter (@RequestBody CenterRequestDto centerRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.rejectCenter(centerRequestDto));
    }

    @PostMapping("/remove-center")
    public ResponseEntity<String> removeCenterRequest (@RequestBody CenterRequestDto centerRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.removeCenterRequest(centerRequestDto));
    }

    @PostMapping("/register-center")
    public ResponseEntity<CenterDto> registerCenter (@RequestBody CenterRequestDto centerRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.registerCenter(centerRequestDto));
    }

    @PostMapping("/fetch-centers")
    public ResponseEntity<List<CenterDto>> fetchCenters(@RequestParam("username") String username) throws TridentException {
        return ResponseEntity.ok(adminService.fetchCenters(username));
    }

    @GetMapping("/all-centers")
    public ResponseEntity<List<CenterDto>> fetchAllCenters() throws TridentException {
        return ResponseEntity.ok(adminService.fetchAllCenters());
    }

//    Batch Region
    @PostMapping("/batch-requests")
    public ResponseEntity<List<BatchRequestDto>> batchRequests (@RequestBody BatchRequestDto batchRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.batchRequests(batchRequestDto));
    }


    @PostMapping("/reject-batch")
    public ResponseEntity<BatchRequestDto> rejectBatch (@RequestBody BatchRequestDto batchRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.rejectBatch(batchRequestDto));
    }

    @PostMapping("/remove-batch")
    public ResponseEntity<String> removeBatchRequest (@RequestBody BatchRequestDto batchRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.removeBatchRequest(batchRequestDto));
    }

    @PostMapping("/create-batch")
    public ResponseEntity<BatchDto> createBatch(@RequestBody BatchRequestDto batchRequestDto) throws TridentException {
        return ResponseEntity.ok(adminService.createBatch(batchRequestDto));
    }

    @GetMapping("/all-batches")
    public ResponseEntity<List<BatchDto>> fetchAllBatches() throws TridentException {
        return ResponseEntity.ok(adminService.fetchAllBatches());
    }

    @PostMapping("/fetch-batches")
    public ResponseEntity<List<BatchDto>> fetchBatches(@RequestParam("centerCode") String centerCode) throws TridentException {
        return ResponseEntity.ok(adminService.fetchBatches(centerCode));
    }

//    Student Region
    @PostMapping("/fetch-students")
    public ResponseEntity<List<StudentDto>> fetchStudents(@RequestParam("batchCode") String batchCode) throws TridentException {
        return ResponseEntity.ok(adminService.fetchStudents(batchCode));
    }

    @GetMapping("/all-students")
    public ResponseEntity<List<StudentDto>> fetchAllStudents() throws TridentException {
        return ResponseEntity.ok(adminService.fetchAllStudents());
    }

    @PostMapping("/fingerprints")
    public ResponseEntity<BiometricsDto> fetchBiometrics (@RequestParam("studentId") Long studentId ) throws TridentException {
        return ResponseEntity.ok(adminService.fetchBiometrics(studentId));
    }
}
