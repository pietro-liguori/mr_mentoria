package com.vili.mrmentoria.api.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vili.mrmentoria.api.domain.dto.EmailDTO;
import com.vili.mrmentoria.api.security.JWTUtil;
import com.vili.mrmentoria.api.security.UserSS;
import com.vili.mrmentoria.api.services.AuthService;
import com.vili.mrmentoria.api.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@RequestBody @Valid EmailDTO dto) {
		service.sendNewPassword(dto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
