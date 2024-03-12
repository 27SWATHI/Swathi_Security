package com.gl.test.GradedAssigment.Service;

import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gl.test.GradedAssigment.Repository.AppUserRepository;
import com.gl.test.GradedAssigment.Security.JwtUtil;
import com.gl.test.GradedAssigment.model.AppUser;

@Service
public class AppUserService implements UserDetailsService {

	@Autowired

	AppUserRepository apprepository;

	@Autowired

	private JwtUtil jwtUtil;

	@PreAuthorize("hasRole('ROLE_ADMIN')")

	public void add(AppUser user) {

		apprepository.save(user);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")

	public List<AppUser> getAll() {

		return apprepository.findAll();

	}

	@Override

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<AppUser> appUser = apprepository.findByName(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		for (String temp : appUser.get().getAuthorities()) {

			GrantedAuthority ga = new SimpleGrantedAuthority(temp);

			grantedAuthorities.add(ga);

		}

		User user = new User(username, appUser.get().getPassword(), grantedAuthorities);

		return user;

	}

	public String login(String name, String password) {

		String token = null;

		Optional<AppUser> appUser = apprepository.findByName(name);

		AppUser user = null;

		if (!appUser.isEmpty()) {

			user = appUser.get();

		}

		PasswordEncoder en = new BCryptPasswordEncoder();

		if (user == null || en.matches(password, user.getPassword()) == false)

		{
			System.out.println("Invalid login");
			token = "Invalid Login";
		} else {

			System.out.println("Login Success");
			token = jwtUtil.generateJwt(user);

		}
		System.out.println("token : " + token);
		return token;
	}

}
