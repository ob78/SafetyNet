package com.safetynet.controllers;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.Flood;
import com.safetynet.entities.urls.PersonInfo;
import com.safetynet.exception.RessourceNotFoundException;
import com.safetynet.model.urls.IResponseUrlsModel;
import com.safetynet.service.endpoints.IFirestationMappingService;
import com.safetynet.service.endpoints.IPersonService;

@RestController
public class UrlsController {

	private static final Logger logger = LoggerFactory.getLogger(UrlsController.class);

	@Autowired
	private IResponseUrlsModel responseModel;

	@Autowired
	private IFirestationMappingService firestationMappingModel;

	@Autowired
	private IPersonService personModel;

	// http://localhost:8080/firestation?stationNumber=<station_number>
	@GetMapping(value = "/firestation")
	public ResponseEntity<Firestation> responseFirestation(@RequestParam String stationNumber) {

		logger.info("Request : GET /firestation");

		if (firestationMappingModel.getFirestationMappingByIdStation(stationNumber) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", stationNumber);
		}

		Firestation responseFirestation = responseModel.responseFirestation(stationNumber);

		logger.info("Success : firestation response found");

		return new ResponseEntity<>(responseFirestation, HttpStatus.OK);
	}

	// http://localhost:8080/childAlert?address=<address>
	@GetMapping(value = "/childAlert")
	public ResponseEntity<ChildAlert> responseChildAlert(@RequestParam String address) {

		logger.info("Request : GET /childAlert");

		if (!personModel.addressExist(address)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		ChildAlert responseChildAlert = responseModel.responseChildAlert(address);

		logger.info("Success : childAlert response found");

		return new ResponseEntity<>(responseChildAlert, HttpStatus.OK);

	}

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@GetMapping(value = "/phoneAlert")
	public ResponseEntity<Set<String>> responsePhoneAlert(@RequestParam String firestation) {

		logger.info("Request : GET /phoneAlert");

		if (firestationMappingModel.getFirestationMappingByIdStation(firestation) == null) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", firestation);
		}

		Set<String> responsePhoneAlert = responseModel.responsePhoneAlert(firestation);

		logger.info("Success : phoneAlert response found");

		return new ResponseEntity<>(responsePhoneAlert, HttpStatus.OK);
	}

	// http://localhost:8080/fire?address=<address>
	@GetMapping(value = "/fire")
	public ResponseEntity<Fire> responseFire(@RequestParam String address) {

		logger.info("Request : GET /fire");

		if (!personModel.addressExist(address)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", address);
		}

		Fire responseFire = responseModel.responseFire(address);

		logger.info("Success : fire response found");

		return new ResponseEntity<>(responseFire, HttpStatus.OK);

	}

	// http://localhost:8080/flood/station?stations=<a list of station_numbers>
	@GetMapping(value = "/flood/stations")
	public ResponseEntity<Flood> responseFlood(@RequestParam String[] station) {

		logger.info("Request : GET /flood");

		for (int i = 0; i < station.length; i++) {

			if (firestationMappingModel.getFirestationMappingByIdStation(station[i]) == null) {
				throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", station[i]);
			}
		}

		Flood responseFlood = responseModel.responseFlood(station);

		logger.info("Success : flood response found");

		return new ResponseEntity<>(responseFlood, HttpStatus.OK);
	}

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping(value = "/personInfo")
	public ResponseEntity<PersonInfo> responsePersonInfo(String firstName, String lastName) {

		logger.info("Request : GET /personInfo");

		if (!personModel.idPersonExist(firstName + lastName)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ",
					firstName + lastName);
		}

		PersonInfo responsePersonInfo = responseModel.responsePersonInfo(firstName, lastName);

		logger.info("Success : personInfo response found");

		return new ResponseEntity<>(responsePersonInfo, HttpStatus.OK);
	}

	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping(value = "/communityEmail")
	public ResponseEntity<List<String>> responseCommunityEmail(@RequestParam String city) {

		logger.info("Request : GET /communityEmail");

		if (!personModel.cityExist(city)) {
			throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Error ressource not found : ", city);
		}

		List<String> responseCommunityEmail = responseModel.responseCommunityEmail(city);

		logger.info("Success : communityEmail response found");

		return new ResponseEntity<>(responseCommunityEmail, HttpStatus.FOUND);
	}

}
