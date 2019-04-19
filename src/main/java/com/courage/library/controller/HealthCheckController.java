package com.courage.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1")
public class HealthCheckController {


	@GetMapping(
			value = "/healthCheck"
	)
	public ResponseEntity<HttpStatus> initialize() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
