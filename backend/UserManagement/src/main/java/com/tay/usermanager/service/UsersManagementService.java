package com.tay.usermanager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tay.usermanager.dto.ReqRes;
import com.tay.usermanager.exception.ExistedUserException;
import com.tay.usermanager.exception.UserNotFoundException;
import com.tay.usermanager.model.Users;
import com.tay.usermanager.repository.UsersRepo;
import com.tay.usermanager.util.JWTUtils;

@Service
public class UsersManagementService {

	@Autowired
	private UsersRepo usersRepo;
	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ReqRes register(ReqRes registrationRequest) throws ExistedUserException {
		ReqRes resp = new ReqRes();

		Optional<Users> userOptional = usersRepo.findByEmail(registrationRequest.getEmail());
		if (userOptional.isPresent()) {
			throw new ExistedUserException("This user has already existed!");
		}

		Users user = new Users();
		user.setEmail(registrationRequest.getEmail());
		user.setCity(registrationRequest.getCity());
		user.setRole(registrationRequest.getRole());
		user.setName(registrationRequest.getName());
		user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

		Users userResult = usersRepo.save(user);
		if (userResult.getId() > 0) {
			resp.setUser(userResult);
			resp.setMessage("User saved successfully");
			resp.setStatusCode(HttpStatus.OK.value());
		}

		return resp;
	}

	public ReqRes login(ReqRes loginRequest) {
		ReqRes resp = new ReqRes();

		// authenticate user
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		Users user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow(
				() -> new UserNotFoundException("User with email '" + loginRequest.getEmail() + "' not found"));
		// create token, refesh token and send back to frontend
		String jwt = jwtUtils.generateToken(user);
		String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
		resp.setStatusCode(HttpStatus.OK.value());
		resp.setToken(jwt);
		resp.setRefreshToken(refreshToken);
		resp.setRole(user.getRole());
		resp.setExpirationTime("10Mins");
		resp.setRefreshTokenExpirationTime("24Hrs");
		resp.setMessage("Successfully logged in");

		return resp;
	}

	public ReqRes refreshToken(ReqRes refreshTokenRequest) {
		ReqRes resp = new ReqRes();

		String email = jwtUtils.extractUsername(refreshTokenRequest.getRefreshToken());
		Users user = usersRepo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User with email '" + email + "' not found"));

		if (jwtUtils.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {
			String jwt = jwtUtils.generateToken(user);
			resp.setStatusCode(HttpStatus.OK.value());
			resp.setToken(jwt);
			resp.setRefreshToken(refreshTokenRequest.getRefreshToken());
			resp.setRefreshTokenExpirationTime("24Hrs");
			resp.setExpirationTime("10Mins");
			resp.setMessage("Successfully refreshed token");
		}

		return resp;
	}

	public ReqRes getAllUsers() {
		ReqRes resp = new ReqRes();

		List<Users> result = usersRepo.findAll();
		if (!result.isEmpty()) {
			resp.setUsersList(result);
			resp.setStatusCode(HttpStatus.OK.value());
			resp.setMessage("Successful");
		} else {
			throw new UserNotFoundException("Users not found");
		}

		return resp;
	}

	public ReqRes getUserById(Integer userId) {
		ReqRes resp = new ReqRes();

		Users userById = usersRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with id '" + userId + "' not found"));
		resp.setUser(userById);
		resp.setStatusCode(HttpStatus.OK.value());
		resp.setMessage("User with id '" + userId + "' found successfully");

		return resp;
	}

	public ReqRes deleteUser(Integer userId) {
		ReqRes resp = new ReqRes();
		Optional<Users> userOptional = usersRepo.findById(userId);
		if (userOptional.isPresent()) {
			usersRepo.deleteById(userId);
			resp.setStatusCode(HttpStatus.OK.value());
			resp.setMessage("User deleted successfully");
		} else {
			throw new UserNotFoundException("User with id '" + userId + "' not found");
		}

		return resp;
	}

	public ReqRes updateUser(Integer userId, Users updatedUser) {
		ReqRes resp = new ReqRes();

		Optional<Users> userOptional = usersRepo.findById(userId);
		if (userOptional.isPresent()) {
			Users existingUser = userOptional.get();
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setName(updatedUser.getName());
			existingUser.setCity(updatedUser.getCity());
			existingUser.setRole(updatedUser.getRole());

			// Check if password is present in the request
			if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
				// Encode the password and update it
				existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
			}

			Users savedUser = usersRepo.save(existingUser);
			resp.setUser(savedUser);
			resp.setStatusCode(HttpStatus.OK.value());
			resp.setMessage("User updated successfully");
		} else {
			throw new UserNotFoundException("User with id '" + userId + "' not found");
		}

		return resp;
	}

	public ReqRes getUserInfo(String email) {
		ReqRes resp = new ReqRes();

		Optional<Users> userOptional = usersRepo.findByEmail(email);
		if (userOptional.isPresent()) {
			resp.setUser(userOptional.get());
			resp.setStatusCode(HttpStatus.OK.value());
			resp.setMessage("successful");
		} else {
			throw new UserNotFoundException("User with email '" + email + "' not found");
		}

		return resp;
	}
}
