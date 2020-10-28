package com.recruitment.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.app.model.ApplicationDto;
import com.recruitment.app.model.Response;
import com.recruitment.app.serviceimpl.CandidateServiceImpl;

@RestController
@RequestMapping("candidate")
public class CandidateController {

	@Autowired
	private CandidateServiceImpl candidateService;
	
	@PostMapping("create-application")
	public Response createOffer(@RequestBody ApplicationDto applicationDto) {
		return candidateService.createApplication(applicationDto);
	}
	
	@GetMapping("application-status/{id}")
	public Response getApplicationStatus(@PathVariable Integer id) {
		return candidateService.getApplicationStatus(id);
	}
	
}
