package com.safetynet.model.endpoints;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.dao.IPersonDao;
import com.safetynet.entities.endpoints.Person;

@Service
public class PersonModelImpl implements IPersonModel {

	private static final Logger logger = LoggerFactory.getLogger(PersonModelImpl.class);

	@Autowired
	private IPersonDao personDao;

	public PersonModelImpl() {
		super();
		logger.info("Constructeur PersonModelImpl sans arg");
	}

	public PersonModelImpl(IPersonDao personDao) {
		super();
		logger.info("Constructeur PersonModelImpl avec arg");
		this.personDao = personDao;
	}

	public IPersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(IPersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public Person addPerson(Person person) {
		return personDao.addPerson(person);
	}

	@Override
	public Person deletePerson(String idPerson) {
		return personDao.deletePerson(idPerson);
	}

	@Override
	public Person updatePerson(Person person) {
		return personDao.updatePerson(person);
	}

	@Override
	public List<Person> getAllPersons() {
		return personDao.getAllPersons();
	}

	@Override
	public Person getPersonById(String idPerson) {
		return personDao.getPersonById(idPerson);
	}

	@Override
	public boolean personExist(Person person) {
		if (personDao.getPersonById(person.getFirstName() + person.getLastName()) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean idPersonExist(String idPerson) {
		List<Person> persons = personDao.getAllPersons();
		for (Person person : persons) {
			if (person.getIdPerson().equals(idPerson)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addressExist(String address) {
		List<Person> persons = personDao.getAllPersons();
		for (Person person : persons) {
			if (person.getAddress().equals(address)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean cityExist(String city) {
		List<Person> persons = personDao.getAllPersons();
		for (Person person : persons) {
			if (person.getCity().equals(city)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<String> getAllAddress() {
		Set<String> address = new HashSet<>();
		List<Person> persons = personDao.getAllPersons();
		for (Person person : persons) {
			address.add(person.getAddress());
		}
		return address;
	}

}
