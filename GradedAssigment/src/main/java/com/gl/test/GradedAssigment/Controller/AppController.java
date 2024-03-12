package com.gl.test.GradedAssigment.Controller;

import java.util.Collection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.test.GradedAssigment.Security.JwtUtil;
import com.gl.test.GradedAssigment.Service.AppUserService;
import com.gl.test.GradedAssigment.Service.EmployeeService;
import com.gl.test.GradedAssigment.model.AppUser;
import com.gl.test.GradedAssigment.model.Employee;

import io.jsonwebtoken.Claims;

@RestController
public class AppController {
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/api/user_Login")
	@ResponseBody
	public String checkLlogin(@RequestParam String name,@RequestParam String password)
	{

	return appUserService.login(name, password);

	}

	 @GetMapping("/add_form")
	 public String employeeHome(Authentication authentication, Model model) {

	     String acceptedRole = "ROLE_admin";
	     boolean roleFound = false;

	   
	     System.out.println("Current login by: " + authentication.getName());

	   
	     Collection<? extends GrantedAuthority> grantedRoles = authentication.getAuthorities();
	     for (GrantedAuthority authority : grantedRoles) {
	         String role = authority.getAuthority();
	         System.out.println("My role: " + role);
	         if (role.equals(acceptedRole)) {
	             roleFound = true;
	             break;      
	             }
	     }

	     if (roleFound) {
	         return "adddetails";
	     } else {
	         List<AppUser> appUsers = appUserService.getAll();
	         model.addAttribute("appUsers", appUsers);
	         return "homepage";
	     }
	 }
	 public boolean verifyToken(String token) {
	Claims claims = null;
	try
	{
	claims = jwtUtil.verify(token);

	} catch (Exception e) {

	e.printStackTrace();
	return false;
	}

	if(claims == null) {
	return false;
	}
	return true;

	}

	 @GetMapping("/getallemployee")
	public List<Employee> getAllEmployee(@RequestParam String token){
	if(verifyToken(token) == false) {
	return null;
	}
	return employeeService.getAll();
	}

	@PostMapping("/api/add_employee")
	public String addNewEmployee (@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname,
	@RequestParam String email, @RequestParam String token)
	{
	Claims claims = null;
	try
	{
	claims = jwtUtil.verify(token);

	} catch (Exception e) {

	e.printStackTrace();
	return "not valid authentication";
	}

	if(claims == null) {
	return "not valid authentication";
	}


	Employee t1 = new Employee(id, firstname, lastname, email);


	Employee duplicateEmployee = employeeService.findById(id);
	if (duplicateEmployee != null) {
	return"Duplicate Id";
	}

	employeeService.add(t1);
	return "";
	}
	@PostMapping("/api/updat_eemployee")

	   public String updateEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname,

	           @RequestParam String email, @RequestParam String token) {

	       if (!verifyToken(token)) {

	           return "Not valid authentication";

	       }



	       Employee employee = new Employee(id, firstname, lastname, email);

	       employeeService.updateEmployee(employee);

	       return "Employee Updated";

	   }





	   @PostMapping("/api/delete_employee")

	   public String deleteEmployee(@RequestParam int id, @RequestParam String token) {

	       if (!verifyToken(token)) {

	           return "Not valid authentication";

	       }



	       employeeService.deleteEmployee(id);

	       return "Employee Deleted";

	   }

	 

	   @GetMapping("/api/get_id")

	   public Employee getById(@RequestParam int id, @RequestParam String token) {

	       if (!verifyToken(token)) {

	           return null;

	       }

	       return employeeService.findById(id);

	   }
	   @GetMapping("/api/search_Firstname")

		public List<Employee> searchFirstname(@RequestParam String firstname,  @RequestParam String token) {

			if (!verifyToken(token)) {

				return null;

			}

			return employeeService.searchByFirstname(firstname);

		}



		@GetMapping("/api/sort_FirstName")

		public List<Employee> sortFirstName( @RequestParam String token) {

			if (!verifyToken(token)) {

				return null;

			}

			return employeeService.sortByFirstNameAscending();

		}
	}

