package com.tay.usermanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tay.usermanager.model.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByEmail(String email);

}
