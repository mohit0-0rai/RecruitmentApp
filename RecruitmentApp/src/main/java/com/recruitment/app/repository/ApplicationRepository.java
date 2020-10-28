package com.recruitment.app.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.app.entity.Application;
import com.recruitment.app.entity.Offer;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer>{
	
	Optional<Application> findById(Integer id);
	
	//@Query("select a from Application a where a.offer=?1 and a.email = ?2")
	Optional<Application> findByOfferAndCandidateEmail(Offer offer, String email);
	
	@Transactional
	@Modifying
	@Query("update Application a set a.applicationStatus = ?1 where a.id = ?2")
	void updateApplicationStatus(String status, Integer applicationId);
	
}
