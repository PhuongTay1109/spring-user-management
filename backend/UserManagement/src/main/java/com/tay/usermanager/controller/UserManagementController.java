package com.tay.usermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tay.usermanager.dto.ReqRes;
import com.tay.usermanager.exception.ExistedUserException;
import com.tay.usermanager.model.Users;
import com.tay.usermanager.service.UsersManagementService;

@RestController
public class UserManagementController {
	
	@Autowired
	private UsersManagementService usersManagementService;

	@PostMapping("/auth/register")
	public ResponseEntity<ReqRes> register(@RequestBody ReqRes req) throws ExistedUserException {
		return ResponseEntity.ok(usersManagementService.register(req));
	}

	@PostMapping("/auth/login")
	public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
		return ResponseEntity.ok(usersManagementService.login(req));
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
		return ResponseEntity.ok(usersManagementService.refreshToken(req));
	}

	@GetMapping("/admin/get-all-users")
	public ResponseEntity<ReqRes> getAllUsers() {
		return ResponseEntity.ok(usersManagementService.getAllUsers());
	}

	@GetMapping("/admin/get-user/{userId}")
	public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId) {
		return ResponseEntity.ok(usersManagementService.getUserById(userId));

	}
	
	@PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody Users req){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, req));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes resp = usersManagementService.getUserInfo(email);
        
        return  ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}
