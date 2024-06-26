package com.tay.usermanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tay.usermanager.dto.ReqRes;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ReqRes> handleGeneralException(Exception e) {
		ReqRes resp = new ReqRes();
		resp.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		resp.setMessage(e.getMessage());
		return ResponseEntity.internalServerError().body(resp);
	}

	@ExceptionHandler(UserNotFoundException.class) 
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ReqRes> handleUserNotFoundException(UserNotFoundException e) {
		ReqRes resp = new ReqRes();
		resp.setStatusCode(HttpStatus.NOT_FOUND.value());
		resp.setMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
	}
	
	@ExceptionHandler(ExistedUserException.class) 
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<ReqRes> handleExistedUserException(ExistedUserException e) {
		ReqRes resp = new ReqRes();
		resp.setStatusCode(HttpStatus.CONFLICT.value());
		resp.setMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
	}

}
