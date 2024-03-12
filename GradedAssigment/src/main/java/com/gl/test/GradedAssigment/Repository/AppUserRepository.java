package com.gl.test.GradedAssigment.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gl.test.GradedAssigment.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer>{

Optional<AppUser> findByName(String username);

}
 