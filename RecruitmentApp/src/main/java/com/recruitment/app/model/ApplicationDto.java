package com.recruitment.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDto {
	
	private Integer offerId;
	private String email;
	private String resumeText;
	
	public Integer getOfferId() {
		return offerId;
	}
	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResumeText() {
		return resumeText;
	}
	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}
	@Override
	public String toString() {
		return "ApplicationDto [offerId=" + offerId + ", email=" + email + ", resumeText=" + resumeText + "]";
	}
	
	
}
