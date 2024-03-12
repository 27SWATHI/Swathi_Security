package com.gl.test.GradedAssigment.Service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.test.GradedAssigment.Repository.EmployeeRepository;
import com.gl.test.GradedAssigment.model.Employee;


@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public String add(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee.isPresent()) {
            return "Employee with ID " + employee.getId() + " already exists";
        } else {
            employeeRepository.save(employee);
            return "Employee added";
        }
    }

    public String updateEmployee(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee.isPresent()) {
        employeeRepository.save(employee);
            return "Employee updated";
        } else {
            return "Employee with ID " + employee.getId() + " does not exist";
        }
    }

    public String deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
        employeeRepository.deleteById(id);
            return "Employee deleted";
        } else {
            return "Employee with ID " + id + " does not exist";
        }
    }

    public Employee findById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> searchByFirstname(String firstname) {
        return employeeRepository.findByFirstnameIgnoreCase(firstname);
    }

    public List<Employee> sortByFirstNameAscending() {
        return employeeRepository.findAllByOrderByFirstnameAsc();
    }
}


