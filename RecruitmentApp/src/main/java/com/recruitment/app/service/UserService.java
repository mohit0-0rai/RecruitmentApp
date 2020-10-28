package com.recruitment.app.service;

import com.recruitment.app.model.OfferDto;
import com.recruitment.app.model.Response;
import com.recruitment.app.model.StatusDto;

public interface UserService {
	
	public Response createOffer(OfferDto offerDto);
	
	public Response viewOffer(Integer offerId);
	
	public Response viewAllOffers();
	
	public Response viewApplication(Integer applicationId);
	
	public Response viewAllApplications();

	public Response updateStatus(StatusDto statusDto);

}
