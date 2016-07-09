
public class MalePerson extends Person {

	public MalePerson() {
		this("", "");
	}

	public MalePerson(String firstName, String lastName) {
		super(firstName, lastName);
		set_gender(Gender.Male);
	}
}
