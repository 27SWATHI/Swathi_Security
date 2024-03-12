package com.gl.test.GradedAssigment.Controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.test.GradedAssigment.Service.EmployeeService;
import com.gl.test.GradedAssigment.model.Employee;

@RestController
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @PostMapping("/addNewEmployee")
    public String addEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname,
@RequestParam String email) {
        Employee employee = new Employee(id, firstname, lastname, email);
        return employeeService.add(employee);
    }

    @PutMapping("/updateEmployee")
    public String updateEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname,
@RequestParam String email) {
        Employee employee = new Employee(id, firstname, lastname, email);
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam int id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/getEmployeeById")
    public Employee getEmployeeById(@RequestParam int id) {
        return employeeService.findById(id);
    }
   
    @GetMapping("/searchbyemployee")
    public List<Employee> searchByFirstname(@RequestParam String firstname) {
        return employeeService.searchByFirstname(firstname);
    }

    @GetMapping("/sortemployee")
    public List<Employee> sortByFirstName() {
        return employeeService.sortByFirstNameAscending();
    }

}


