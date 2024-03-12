package com.gl.test.GradedAssigment.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gl.test.GradedAssigment.model.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

List<Employee> findByFirstnameIgnoreCase(String firstname);

List<Employee> findAllByOrderByFirstnameAsc();

}
