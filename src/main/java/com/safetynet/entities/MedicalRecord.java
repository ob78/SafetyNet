package com.safetynet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MedicalRecord {

	@JsonIgnore
	private String idMedicalRecord;
	private String firstName;
	private String lastName;
	private String birthdate;
	private String[] medications;
	private String[] allergies;

	public MedicalRecord() {
		super();
	}

	public MedicalRecord(String idMedicalRecord, String firstName, String lastName, String birthdate,
			String[] medications, String[] allergies) {
		super();
		this.idMedicalRecord = idMedicalRecord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getIdMedicalRecord() {
		return idMedicalRecord;
	}

	public void setIdMedicalRecord(String idMedicalRecord) {
		this.idMedicalRecord = idMedicalRecord;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String[] getMedications() {
		return medications;
	}

	public void setMedications(String[] medications) {
		this.medications = medications;
	}

	public String[] getAllergies() {
		return allergies;
	}

	public void setAllergies(String[] allergies) {
		this.allergies = allergies;
	}

}
