package com.recruitment.app.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruitment.app.entity.Application;
import com.recruitment.app.entity.Offer;
import com.recruitment.app.model.OfferDto;
import com.recruitment.app.model.Response;
import com.recruitment.app.model.StatusDto;
import com.recruitment.app.repository.ApplicationRepository;
import com.recruitment.app.repository.OfferRepository;
import com.recruitment.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	public Response createOffer(OfferDto offerDto) {
		Response response = new Response();
		logger.info("Request: Create offer " + offerDto);

		try {
			boolean validationStatus = validateOffer(offerDto, response);
			if (!validationStatus)
				return response;

			Offer offer = new Offer();
			offer.setJobTitle(offerDto.getJobTitle());
			Integer offerId = offerRepository.save(offer).getId();
			response.setCode("201");
			response.setMessage("Offer created");
			Map<String, Object> data = new HashMap<>();
			data.put("offerId", offerId);
			response.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Technical error, Please try again after some time.");
		} finally {
			logger.info("Response to user: " + response);
		}
		return response;
	}

	public Response viewOffer(Integer offerId) {
		Response response = new Response();
		logger.info("Request: View offer " + offerId);
		try {
			if (null == offerId) {
				response.setCode("400");
				response.setMessage("Bad Request: offer id cannot be null");
				return response;
			}

			Optional<Offer> offer = offerRepository.findById(offerId);

			if (!offer.isPresent()) {
				response.setCode("400");
				response.setMessage("Bad Request: Invalid offer id");
				return response;
			}

			response.setCode("200");
			response.setMessage("Success");
			Map<String, Object> data = new HashMap<>();
			data.put("offer", offer);
			response.setData(data);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Technical error, Please try again after some time");
		} finally {
			logger.info("Response to user: " + response);
		}

		return response;
	}

	public Response viewAllOffers() {
		Response response = new Response();
		logger.info("Request: View all offers ");
		try {

			List<Offer> allOffers = (List<Offer>) offerRepository.findAll();
			if (null == allOffers) {
				response.setCode("400");
				response.setMessage("No offers found");
				return response;
			}
			response.setCode("200");
			response.setMessage("Success");
			Map<String, Object> data = new HashMap<>();
			data.put("allOffers", allOffers);
			response.setData(data);

		} catch (Exception e) {
			response.setCode("500");
			response.setMessage("Technical error, please try again after some time.");
		} finally {
			logger.info("Response to user: " + response);
		}
		return response;
	}

	public Response viewApplication(Integer applicationId) {
		Response response = new Response();
		logger.info("Request: View application " + applicationId);
		try {
			if (null == applicationId) {
				response.setCode("400");
				response.setMessage("Bad Request: application id cannot be null");
				return response;
			}

			Optional<Application> application = applicationRepository.findById(applicationId);

			if (!application.isPresent()) {
				response.setCode("400");
				response.setMessage("Bad Request: Invalid application id");
				return response;
			}

			response.setCode("200");
			response.setMessage("Success");
			Map<String, Object> data = new HashMap<>();
			data.put("application", application);
			response.setData(data);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Technical error, Please try again after some time");
		} finally {
			logger.info("Response to user: " + response);
		}

		return response;
	}

	public Response viewAllApplications() {
		Response response = new Response();
		logger.info("Request: View all applications");
		try {

			List<Application> allApplications = (List<Application>) applicationRepository.findAll();
			if (null == allApplications || allApplications.isEmpty()) {
				response.setCode("400");
				response.setMessage("No application found");
				return response;
			}
			response.setCode("200");
			response.setMessage("Success");
			Map<String, Object> data = new HashMap<>();
			data.put("allApplications", allApplications);
			response.setData(data);

		} catch (Exception e) {
			response.setCode("500");
			response.setMessage("Technical error, please try again after some time.");
		} finally {
			logger.info("Response to user: " + response);
		}
		return response;
	}

	private boolean validateOffer(OfferDto offerDto, Response response) throws Exception {
		if (null == offerDto || null == offerDto.getJobTitle() || offerDto.getJobTitle().isEmpty()) {
			response.setCode("400");
			response.setMessage("Bad Request: Job title cannot be empty.");
			return false;
		}
		Optional<Offer> offerOpt = offerRepository.findByJobTitle(offerDto.getJobTitle());
		if (offerOpt.isPresent()) {
			response.setCode("400");
			response.setMessage("Bad Request: Offer already created");
		}
		return true;
	}

	@Override
	public Response updateStatus(StatusDto statusDto) {
		Response response = new Response();
		try {
			if (null == statusDto || statusDto.getApplicationId() == null) {
				response.setCode("400");
				response.setMessage("Bad Request: Application id cannot be empty");
				return response;
			}

			if (null == statusDto || statusDto.getApplicationStatus() == null
					|| statusDto.getApplicationStatus().isEmpty()) {
				response.setCode("400");
				response.setMessage("Bad Request: Application status cannot be empty");
				return response;
			}

			if (!statusDto.getApplicationStatus().equalsIgnoreCase("APPLIED")
					&& !statusDto.getApplicationStatus().equalsIgnoreCase("INVITED")
					&& !statusDto.getApplicationStatus().equalsIgnoreCase("REJECTED")
					&& !statusDto.getApplicationStatus().equalsIgnoreCase("HIRED")) {
				response.setCode("400");
				response.setMessage("Bad Request: Invalid application status.");
				return response;
			}

			Optional<Application> applicationOpt = applicationRepository.findById(statusDto.getApplicationId());

			if (!applicationOpt.isPresent()) {
				response.setCode("400");
				response.setMessage("Bad Request: application not found");
				return response;
			}

			applicationRepository.updateApplicationStatus(statusDto.getApplicationStatus(),
					statusDto.getApplicationId());
			response.setCode("200");
			response.setMessage("Status updated successfully");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("500");
			response.setMessage("Technical error, please try again after some time.");
		} finally {
			logger.info("Response to user: " + response);
		}
		return response;
	}

}
