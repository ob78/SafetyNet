package com.safetynet.entities.urls;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Firestation {

	@JsonProperty("PersonsCoveredByFirestation")
	private List<FirestationPerson> firestationPersons;

	@JsonProperty("NumberOfAdults")
	private int nbAdults;

	@JsonProperty("NumberOfChilds")
	private int nbChilds;

	public Firestation() {
		super();
	}

	public Firestation(List<FirestationPerson> firestationPersons, int nbAdults, int nbChilds) {
		super();
		this.firestationPersons = firestationPersons;
		this.nbAdults = nbAdults;
		this.nbChilds = nbChilds;
	}

	public List<FirestationPerson> getFirestationPersons() {
		return firestationPersons;
	}

	public void setFirestationPersons(List<FirestationPerson> firestationPersons) {
		this.firestationPersons = firestationPersons;
	}

	public int getNbAdults() {
		return nbAdults;
	}

	public void setNbAdults(int nbAdults) {
		this.nbAdults = nbAdults;
	}

	public int getNbChilds() {
		return nbChilds;
	}

	public void setNbChilds(int nbChilds) {
		this.nbChilds = nbChilds;
	}

}
