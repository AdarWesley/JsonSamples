package org.awesley;

public class MalePerson extends Person {

	public MalePerson() {
		this("", "");
	}

	public MalePerson(String firstName, String lastName) {
		super(firstName, lastName);
	}

	@Override
	public Gender get_Gender() {
		return Gender.Male;
	}
}
