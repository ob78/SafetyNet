package com.safetynet.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.entities.endpoints.FirestationMapping;
import com.safetynet.model.endpoints.IFirestationMappingModel;

@RestController
public class SafetyNetEndpointsFirestationsController {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetEndpointsFirestationsController.class);

	@Autowired
	private IFirestationMappingModel firestationMappingModel;

	@GetMapping(value = "/firestations")
	public ResponseEntity<List<FirestationMapping>> getAllFirestationMappings() {

		List<FirestationMapping> firestationMappings = firestationMappingModel.getAllFirestationMappings();

		logger.info("Success : firestation mappings list found");

		return new ResponseEntity<>(firestationMappings, HttpStatus.FOUND);
	}

	@PostMapping(value = "/firestations")
	public ResponseEntity<FirestationMapping> addFirestationMapping(
			@RequestBody FirestationMapping firestationMapping) {

		if (firestationMappingModel.firestationMappingExist(firestationMapping)) {
			logger.error("Error : firestation mapping already exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		FirestationMapping firestationMappingToAdd = new FirestationMapping();

		firestationMappingToAdd.setAddress(firestationMapping.getAddress());
		firestationMappingToAdd.setStation(firestationMapping.getStation());

		firestationMappingModel.addFirestationMapping(firestationMappingToAdd);

		if (!firestationMappingModel.firestationMappingExist(firestationMappingToAdd)) {
			logger.error("Error : firestation mapping not added");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : firestation mapping added");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(firestationMappingToAdd.getAddress()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/firestations/{address}")
	public ResponseEntity<FirestationMapping> updateFirestationMapping(@PathVariable String address,
			@RequestBody FirestationMapping firestationMapping) {

		FirestationMapping firestationMappingToUpdate = firestationMappingModel.getFirestationMappingByAdress(address);

		if (firestationMappingToUpdate == null) {
			logger.error("Error : firestation mapping adress does not exist");
			return new ResponseEntity<>(firestationMapping, HttpStatus.NOT_FOUND);
		}

		firestationMappingToUpdate.setStation(firestationMapping.getStation());

		FirestationMapping firestationMappingUpdated = firestationMappingModel
				.updateFirestationMapping(firestationMappingToUpdate);

		return new ResponseEntity<>(firestationMappingUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/firestations/{address}")
	public ResponseEntity<Void> deleteFirestationMapping(@PathVariable(value = "address") String address) {

		FirestationMapping firestationMappingToDelete = firestationMappingModel.getFirestationMappingByAdress(address);

		if (firestationMappingToDelete == null) {
			logger.error("Error : firestation mapping address does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		FirestationMapping firestationMappingDeleted = firestationMappingModel.deleteFirestationMapping(address);

		if (firestationMappingModel.firestationMappingExist(firestationMappingDeleted)) {
			logger.error("Error : firestation mapping not deleted");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Success : firestation mapping deleted");
		return new ResponseEntity<>(HttpStatus.GONE);
	}

}
