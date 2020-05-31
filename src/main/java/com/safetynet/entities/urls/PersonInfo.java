package com.safetynet.entities.urls;

import java.util.List;

public class PersonInfo {
	
	private String firstName;
	private String lastName;
	private long age;
	private String email;
	private String[] medications;
	private String[] allergies;
	private List<PersonInfoSameName> otherPersonsWithSameName;
	
	public PersonInfo() {
		super();
	}

	public PersonInfo(String firstName, String lastName, long age, String email, String[] medications,
			String[] allergies, List<PersonInfoSameName> otherPersonsWithSameName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.medications = medications;
		this.allergies = allergies;
		this.otherPersonsWithSameName = otherPersonsWithSameName;
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

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<PersonInfoSameName> getOtherPersonsWithSameName() {
		return otherPersonsWithSameName;
	}

	public void setOtherPersonsWithSameName(List<PersonInfoSameName> otherPersonsWithSameName) {
		this.otherPersonsWithSameName = otherPersonsWithSameName;
	}
	
}