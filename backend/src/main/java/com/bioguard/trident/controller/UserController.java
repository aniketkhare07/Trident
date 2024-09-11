package com.bioguard.trident.controller;

import com.bioguard.trident.dto.*;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.service.AdminService;
import com.bioguard.trident.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    static Log logger = LogFactory.getLog(UserController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi User");
    }

    @PostMapping("/request-center")
    public ResponseEntity<CenterRequestDto> requestCenter(@RequestBody CenterRequestDto centerRequestDto) throws TridentException {
        return ResponseEntity.ok(userService.requestCenter(centerRequestDto));
    }

    @PostMapping("/request-batch")
    public ResponseEntity<BatchRequestDto> requestBatch(@RequestBody BatchRequestDto batchRequestDto) throws TridentException {
        return ResponseEntity.ok(userService.requestBatch(batchRequestDto));
    }

    @PostMapping("/fetch-centers")
    public ResponseEntity<List<CenterDto>> fetchCenters(@RequestParam("username") String username) throws TridentException {
        return ResponseEntity.ok(adminService.fetchCenters(username));
    }

    @PostMapping("/fetch-batches")
    public ResponseEntity<List<BatchDto>> fetchBatches(@RequestParam("centerCode") String centerCode) throws TridentException {
        return ResponseEntity.ok(adminService.fetchBatches(centerCode));
    }

    @PostMapping("/fetch-students")
    public ResponseEntity<List<StudentDto>> fetchStudents(@RequestParam("batchCode") String batchCode) throws TridentException {
        return ResponseEntity.ok(adminService.fetchStudents(batchCode));
    }

    @PostMapping("/fingerprints")
    public ResponseEntity<BiometricsDto> fetchBiometrics (@RequestParam("studentId") Long studentId ) throws TridentException {
        return ResponseEntity.ok(adminService.fetchBiometrics(studentId));
    }
}
