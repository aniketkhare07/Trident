package com.bioguard.trident.controller;

import com.bioguard.trident.dto.BatchDto;
import com.bioguard.trident.dto.CenterDto;
import com.bioguard.trident.dto.StudentDto;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.service.AdminService;
import com.bioguard.trident.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<String> hello () {
        return ResponseEntity.ok ("Hi student");
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDto> registerStudent (@RequestBody StudentDto studentDto) throws TridentException {
        return ResponseEntity.ok(studentService.registerStudent(studentDto));
    }

    @PostMapping("/fetch-centers")
    public ResponseEntity<List<CenterDto>> fetchCenters(@RequestParam("username") String username) throws TridentException {
        return ResponseEntity.ok(adminService.fetchCenters(username));
    }

    @PostMapping("/fetch-batches")
    public ResponseEntity<List<BatchDto>> fetchBatches(@RequestParam("centerCode") String centerCode) throws TridentException {
        return ResponseEntity.ok(adminService.fetchBatches(centerCode));
    }
}
