package com.recruitment.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.app.entity.Offer;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Integer>{

	Optional<Offer> findById(Integer id);
	
	Optional<Offer> findByJobTitle(String jobTitle);
	
}
