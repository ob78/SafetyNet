package com.safetynet.service.endpoints;

import java.util.List;
import java.util.Set;

import com.safetynet.entities.endpoints.Person;

public interface IPersonService {
	public Person addPerson(Person person);

	public Person deletePerson(String idPerson);

	public Person updatePerson(Person person);

	public List<Person> getAllPersons();

	public Person getPersonById(String idPerson);

	public boolean personExist(Person person);

	public boolean idPersonExist(String idPerson);

	public boolean addressExist(String address);

	public boolean cityExist(String city);

	public Set<String> getAllAddress();
}