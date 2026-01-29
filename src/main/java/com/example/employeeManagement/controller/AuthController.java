package com.example.employeeManagement.controller;

import com.example.employeeManagement.DTO.LoginRequest;
import com.example.employeeManagement.DTO.LoginResponse;
import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.repository.EmployeeRepository;
import com.example.employeeManagement.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final EmployeeRepository repository;
    private final JwtUtil jwtUtil;

    public AuthController(EmployeeRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Employee employee = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.generateToken(employee.getUsername());

        return ResponseEntity.ok(
                new LoginResponse(employee.getUsername(), token)
        );
    }
}
