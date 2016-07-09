
public class FemalePerson extends Person {

	public FemalePerson() {
		this("", "");
	}

	public FemalePerson(String firstName, String lastName) {
		super(firstName, lastName);
		set_gender(Gender.Female);
	}
}
