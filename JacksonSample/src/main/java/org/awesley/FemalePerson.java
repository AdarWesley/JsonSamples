package org.awesley;

public class FemalePerson extends Person {

	public FemalePerson() {
		this("", "");
	}

	public FemalePerson(String firstName, String lastName) {
		super(firstName, lastName);
	}

	@Override
	public Gender get_Gender() {
		return Gender.Female;
	}
}
