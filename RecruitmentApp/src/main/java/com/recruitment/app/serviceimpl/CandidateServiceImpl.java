package com.recruitment.app.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruitment.app.entity.Application;
import com.recruitment.app.entity.Offer;
import com.recruitment.app.model.ApplicationDto;
import com.recruitment.app.model.Response;
import com.recruitment.app.repository.ApplicationRepository;
import com.recruitment.app.repository.OfferRepository;
import com.recruitment.app.service.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService{
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	private static final Logger logger = LogManager.getLogger();

	public Response createApplication(ApplicationDto applicationDto) {
		Response response = new Response();
		logger.info("Request: Create application " + applicationDto);
		
		try {
			boolean validationStatus = validateApplication(applicationDto, response);
			if(!validationStatus) {
				return response;
			}
			Optional<Offer> offerOpt = offerRepository.findById(applicationDto.getOfferId());
			if(!offerOpt.isPresent()) {
				response.setCode("400");
				response.setMessage("Bad Request: Invalid offer id");
				return response;
			}
			Offer offer = offerOpt.get();
			boolean isOfferApplied = applicationRepository.findByOfferAndCandidateEmail(offer, applicationDto.getEmail()).isPresent(); 
			if(isOfferApplied) {
				response.setCode("400");
				response.setMessage("Bad Request: Offer already associated with current email");
				return response;
			}
			Application application = new Application();
			application.setOffer(offer);
			application.setApplicationStatus("APPLIED");
			application.setCandidateEmail(applicationDto.getEmail());
			application.setResumeText(applicationDto.getResumeText());
			Integer applicationId = applicationRepository.save(application).getId();
			response.setCode("201");
			response.setMessage("Application Created");
			Map<String, Object> data = new HashMap<>();
			data.put("applicationId", applicationId);
			response.setData(data);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Technical Error, Please try after sometime.");
		} finally {
			logger.info("Response to user: " + response);
		}
		
		return response;
	}

	public Response getApplicationStatus(Integer applicationId) {
		Response response = new Response();
		logger.info("Request: Application status " + applicationId);
		try {
			if(null == applicationId) {
				response.setCode("400");
				response.setMessage("Bad Request: Application id cannot be null");
				return response;
			}
			Optional<Application> application = applicationRepository.findById(applicationId);
			if(!application.isPresent()) {
				response.setCode("400");
				response.setMessage("Bad Request: Invalid application id");
				return response;
			}
			response.setCode("200");
			response.setMessage("Success");
			Map<String, Object> map = new HashMap<>();
			map.put("applicationStatus", application.get().getApplicationStatus());
			response.setData(map);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Technical error, Please try after sometime.");
		}
		finally {
			logger.info("Response to user (Application Status): " + response);
		}
		return response;
	}
	
	public boolean validateApplication(ApplicationDto applicationDto, Response response) {
		if(null == applicationDto.getOfferId()) {
			response.setCode("400");
			response.setMessage("Bad Request: Offer id cannot be empty");
			return false;
		}
		
		if(null == applicationDto.getEmail() || applicationDto.getEmail().isEmpty()) {
			response.setCode("400");
			response.setMessage("Bad Request: Email cannot be empty");
			return false;
		}
		else {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";
			Pattern pat = Pattern.compile(emailRegex);
			boolean regexStatus = pat.matcher(applicationDto.getEmail()).matches();
			if(!regexStatus) {
				response.setCode("400");
				response.setMessage("Bad Request: Invalid email");
				return false;	
			}
		}
		
		if(null == applicationDto.getResumeText() || applicationDto.getResumeText().isEmpty()) {
			response.setCode("400");
			response.setMessage("Bad Request: Resume text cannot be empty");
			return false;
		}
		return true;
	}

}
