import java.util.ArrayList;

public class Family {

	private ArrayList<Person> familyMembers;

	public Family(ArrayList<Person> familyMembers) {
		this.setFamilyMembers(familyMembers);
	}

	public Family() {
		familyMembers = new ArrayList<Person>();
	}

	public ArrayList<Person> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(ArrayList<Person> familyMembers) {
		this.familyMembers = familyMembers;
	}

}
