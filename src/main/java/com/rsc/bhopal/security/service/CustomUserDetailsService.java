package com.rsc.bhopal.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service	
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		  UserDetails admin = User.withUsername("user") 
	              .password(encoder.encode("user")) 
	              .roles("USER") 
	              .build(); 

	      UserDetails user = User.withUsername("admin") 
	              .password(encoder.encode("admin")) 
	              .roles("ADMIN") 
	              .build();
	      if(username.equals("user")) {
	    	  return admin;
	      }else if (username.equals("admin")) {
	    	  return user;
	      }else {
	    	  throw new UsernameNotFoundException("User not found");
	      }
	}

}
