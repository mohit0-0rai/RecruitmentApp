package com.recruitment.app.service;

import com.recruitment.app.model.ApplicationDto;
import com.recruitment.app.model.Response;

public interface CandidateService {

	public Response createApplication(ApplicationDto applicationDto);
	
	public Response getApplicationStatus(Integer applicationId);
	
}
