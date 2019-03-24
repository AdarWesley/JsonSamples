package org.awesley;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Person {

	private String _firstName;
	private String _lastName;
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	private List<Person> _children = new ArrayList<Person>();

	public Person() {
	}
	
	public Person(String firstName, String lastName){
		this._firstName = firstName;
		this._lastName = lastName;
	}
	
	public String get_firstName() {
		return _firstName;
	}
	public void set_firstName(String firstName) {
		this._firstName = firstName;
	}
	
	public String get_lastName() {
		return _lastName;
	}
	public void set_lastName(String lastName) {
		this._lastName = lastName;
	}
	
	public List<Person> get_children() {
		return _children;
	}
	public void set_children(Collection<? extends Person> children) {
		this._children.clear();
		this._children.addAll(children);
	}
	
	@JsonIgnore
	public abstract Gender get_Gender();
	
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles.clear();
		this.vehicles.addAll(vehicles);
	}
	
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_children == null) ? 0 : _children.hashCode());
		result = prime * result + ((_firstName == null) ? 0 : _firstName.hashCode());
		result = prime * result + ((_lastName == null) ? 0 : _lastName.hashCode());
		result = prime * result + ((vehicles == null) ? 0 : vehicles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (_children == null) {
			if (other._children != null)
				return false;
		} else if (!_children.equals(other._children))
			return false;
		if (_firstName == null) {
			if (other._firstName != null)
				return false;
		} else if (!_firstName.equals(other._firstName))
			return false;
		if (_lastName == null) {
			if (other._lastName != null)
				return false;
		} else if (!_lastName.equals(other._lastName))
			return false;
		if (vehicles == null) {
			if (other.vehicles != null)
				return false;
		} else if (!vehicles.equals(other.vehicles))
			return false;
		return true;
	}
}
