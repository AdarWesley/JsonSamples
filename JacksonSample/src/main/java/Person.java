import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Person {
	private String _firstName;
	private String _lastName;
	private List<Vehicle> vehicles;
	private ArrayList<Person> _children = new ArrayList<Person>();

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
	
	public ArrayList<Person> get_children() {
		return _children;
	}
	public void set_children(Collection<? extends Person> children) {
		this._children.clear();
		this._children.addAll(children);
	}
	public Person() {
	}
	
	public Person(String firstName, String lastName){
		this._firstName = firstName;
		this._lastName = lastName;
	}
	
	@JsonIgnore
	public abstract Gender get_Gender();
	
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
}
