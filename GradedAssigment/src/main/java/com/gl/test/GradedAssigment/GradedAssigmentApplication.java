package com.gl.test.GradedAssigment;
import java.util.HashSet;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gl.test.GradedAssigment.Service.AppUserService;
import com.gl.test.GradedAssigment.model.AppUser;



@SpringBootApplication
public class GradedAssigmentApplication  implements CommandLineRunner{

@Autowired
AppUserService appUserService;

public static void main(String[] args) {
SpringApplication.run(GradedAssigmentApplication.class, args);
}

@Override
public void run(String... args) throws Exception {

Set<String> authAdmin = new HashSet<>();
authAdmin.add("Admin");

Set<String> authUser = new HashSet<>();
authUser.add("User");

Set<String> authAll = new HashSet<>();
authAll.add("User");
authAll.add("Admin");


PasswordEncoder en = new BCryptPasswordEncoder();

AppUser appAdmin = new AppUser(1, "admin", "admin", en.encode("adminPassword"), authAdmin);
appUserService.add (appAdmin);


AppUser appUser = new AppUser (2, "user", "user", en.encode("userPassword"), authUser);
appUserService.add(appUser);

AppUser appAll = new AppUser (3, "superuser", "superuser", en.encode("SuperPassword "),authAll);
appUserService.add(appAll);

}

}


