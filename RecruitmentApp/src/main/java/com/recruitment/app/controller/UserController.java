package com.recruitment.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.app.model.OfferDto;
import com.recruitment.app.model.Response;
import com.recruitment.app.model.StatusDto;
import com.recruitment.app.service.UserService;

@RestController
@RequestMapping(value="user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("create-offer")
	public Response createOffer(@RequestBody OfferDto offerDto) {
		return userService.createOffer(offerDto);
	}
	
	@GetMapping("view-offer/{id}")
	public Response viewOffer(@PathVariable Integer id) {
		return userService.viewOffer(id);
	}
	
	@GetMapping("view-all-offers")
	public Response viewAllOffers() {
		return userService.viewAllOffers();
	}
	
	@GetMapping("view-application/{id}")
	public Response viewApplication(@PathVariable Integer id) {
		return userService.viewApplication(id);
	}
	
	@GetMapping("view-all-applications")
	public Response viewAllApplications() {
		return userService.viewAllApplications();
	}
	
	@PostMapping("update-status")
	public Response updateStatus(@RequestBody StatusDto statusDto) {
		return userService.updateStatus(statusDto);
	}
	
}
