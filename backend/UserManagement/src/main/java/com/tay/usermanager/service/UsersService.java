package com.tay.usermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tay.usermanager.repository.UsersRepo;

@Service
public class UsersService implements UserDetailsService {

	@Autowired 
	UsersRepo usersRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usersRepo.findByEmail(username).orElseThrow();
	}

}
