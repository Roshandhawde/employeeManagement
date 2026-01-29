package com.example.employeeManagement.controller;

import com.example.employeeManagement.entity.Employee;
import com.example.employeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
@Autowired
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // View all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Add employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee emp) {

        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setUsername(emp.getUsername());
        existing.setAge(emp.getAge());
        existing.setDob(emp.getDob());
        existing.setPassword(emp.getPassword());

        return repository.save(existing);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

