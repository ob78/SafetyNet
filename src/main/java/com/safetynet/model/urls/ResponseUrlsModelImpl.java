package com.safetynet.model.urls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.entities.endpoints.Person;
import com.safetynet.entities.urls.ChildAlert;
import com.safetynet.entities.urls.ChildAlertChild;
import com.safetynet.entities.urls.Fire;
import com.safetynet.entities.urls.FirePerson;
import com.safetynet.entities.urls.Firestation;
import com.safetynet.entities.urls.FirestationPerson;
import com.safetynet.model.endpoints.IFirestationMappingModel;
import com.safetynet.model.endpoints.IMedicalRecordModel;
import com.safetynet.model.endpoints.IPersonModel;

@Component
public class ResponseUrlsModelImpl implements IResponseUrlsModel {

	@Autowired
	private IPersonModel personModel;

	@Autowired
	private IFirestationMappingModel firestationMappingModel;

	@Autowired
	private IMedicalRecordModel medicalRecordModel;

	@Override
	public Firestation responseFirestation(int stationNumber) {

		Firestation responseFirestation = new Firestation();

		List<FirestationPerson> responseFirestationListPersons = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		int nbAdults = 0;
		int nbChilds = 0;

		for (Person personInList : listAllPersons) {

			if ((firestationMappingModel.getFirestationMappingByAdress(personInList.getAddress())
					.getStation()) == stationNumber) {

				FirestationPerson responseFirestationPerson = new FirestationPerson();
				responseFirestationPerson.setFirstName(personInList.getFirstName());
				responseFirestationPerson.setLastName(personInList.getLastName());
				responseFirestationPerson.setAddress(personInList.getAddress());
				responseFirestationPerson.setPhone(personInList.getPhone());

				responseFirestationListPersons.add(responseFirestationPerson);

				long personAge = getPersonAge(personInList);

				if (personAge <= 18) {
					nbChilds++;
				} else {
					nbAdults++;
				}
			}

			responseFirestation.setFirestationPersons(responseFirestationListPersons);
			responseFirestation.setNbAdults(nbAdults);
			responseFirestation.setNbChilds(nbChilds);
		}

		return responseFirestation;

	}

	@Override
	public ChildAlert responseChildAlert(String address) {

		ChildAlert responseChildAlert = new ChildAlert();

		List<ChildAlertChild> responseChildAlertListChilds = new ArrayList<>();

		List<Person> responseChildAlertListHouseholdMembers = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		for (Person personInList : listAllPersons) {

			if (personInList.getAddress().equals(address)) {

				long personAge = getPersonAge(personInList);

				if (personAge <= 18) {
					ChildAlertChild child = new ChildAlertChild();
					child.setChildFirstName(personInList.getFirstName());
					child.setChildLastName(personInList.getLastName());
					child.setChildAge(personAge);
					responseChildAlertListChilds.add(child);
				} else {
					Person otherHouseholdMember = new Person();
					otherHouseholdMember.setFirstName(personInList.getFirstName());
					otherHouseholdMember.setLastName(personInList.getLastName());
					otherHouseholdMember.setAddress(personInList.getAddress());
					otherHouseholdMember.setCity(personInList.getCity());
					otherHouseholdMember.setZip(personInList.getZip());
					otherHouseholdMember.setPhone(personInList.getPhone());
					otherHouseholdMember.setEmail(personInList.getEmail());
					responseChildAlertListHouseholdMembers.add(otherHouseholdMember);
				}

				responseChildAlert.setChildAlertChilds(responseChildAlertListChilds);
				responseChildAlert.setChildAlertHouseholdMembers(responseChildAlertListHouseholdMembers);
			}
		}

		if (responseChildAlertListChilds.isEmpty()) {
			return null;
		}

		return responseChildAlert;
	}

	@Override
	public Fire responseFire(String address) {

		Fire responseFire = new Fire();

		List<FirePerson> responseFirePerson = new ArrayList<>();

		List<Person> listAllPersons = personModel.getAllPersons();

		int nbStation = firestationMappingModel.getFirestationMappingByAdress(address).getStation();

		responseFire.setNbStation(nbStation);

		for (Person personInList : listAllPersons) {

			if (personInList.getAddress().equals(address)) {

				FirePerson firePerson = new FirePerson();

				long personAge = getPersonAge(personInList);

				firePerson.setFirstName(personInList.getFirstName());
				firePerson.setLastName(personInList.getLastName());
				firePerson.setPhone(personInList.getPhone());
				firePerson.setAge(personAge);

				firePerson.setMedications(medicalRecordModel
						.getMedicalRecordById(firePerson.getFirstName() + firePerson.getLastName()).getMedications());
				firePerson.setAllergies(medicalRecordModel
						.getMedicalRecordById(firePerson.getFirstName() + firePerson.getLastName()).getAllergies());

				responseFirePerson.add(firePerson);
			}

		}

		responseFire.setFirePersons(responseFirePerson);

		return responseFire;
	}

	private long getPersonAge(Person person) {

		String personBirthdateString = medicalRecordModel.getMedicalRecordById(person.getIdPerson()).getBirthdate();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate personBirthdateDate = LocalDate.parse(personBirthdateString, formatter);

		return ChronoUnit.YEARS.between(personBirthdateDate, LocalDate.now());
	}

}
