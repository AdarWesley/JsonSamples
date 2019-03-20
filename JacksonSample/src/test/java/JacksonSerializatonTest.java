import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.Assert.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonSerializatonTest {

	private Family family1;
	private String _expectedJsonFamily = 
			"{" +
			"   familyMembers: [ {" +
			"	  \"@class\" : \"MalePerson\"," +
			"	  \"@id\" : 1," +
			"	  \"_firstName\" : \"Child1\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"vehicles\" : [ {" +
			"	    \"color\" : \"Red\"" +
			"	  }, {" +
			"	    \"color\" : \"Blue\"" +
			"	  } ]," +
			"	  \"_children\" : [ ]" +
			"	}, {" +
			"	  \"@class\" : \"FemalePerson\"," +
			"	  \"@id\" : 2," +
			"	  \"_firstName\" : \"Child2\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"vehicles\" : [ {" +
			"	    \"color\" : \"Blue\"" +
			"	  } ]," +
			"	  \"_children\" : [ ]" +
			"	}, {" +
			"	  \"@class\" : \"MalePerson\"," +
			"	  \"@id\" : 3," +
			"	  \"_firstName\" : \"Father\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"vehicles\" : [ {" +
			"	    \"engineSize\" : 2000," +
			"	    \"color\" : \"Black\"" +
			"	  }, {" +
			"	    \"engineSize\" : 1600," +
			"	    \"color\" : \"White\"" +
			"	  } ]," +
			"	  \"_children\" : [ 1, 2 ]" +
			"	}, {" +
			"	  \"@class\" : \"FemalePerson\"," +
			"	  \"@id\" : 4," +
			"	  \"_firstName\" : \"Mother\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"vehicles\" : [ {" +
			"	    \"engineSize\" : 1600," +
			"	    \"color\" : \"White\"" +
			"	  }, {" +
			"	    \"color\" : \"Blue\"" +
			"	  } ]," +
			"	  \"_children\" : [ 1, 2 ]" +
			"	} ]" +
			"}";
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Person> familyMembers = new ArrayList<Person>();
		Vehicle jeep = new Jeep(2000, "Black");
		Vehicle sedan = new Sedan(1600, "White");
		Vehicle corcinet = new Corcinet("Red");
		Vehicle bicycle = new Bicycle("Blue");
		familyMembers.add(createPerson(Gender.Male, "Child1", "Lastname", Arrays.asList(corcinet, bicycle)));
		familyMembers.add(createPerson(Gender.Female, "Child2", "Lastname", Arrays.asList(bicycle)));
		familyMembers.add(createPerson(Gender.Male, "Father", "Lastname", Arrays.asList(jeep, sedan)));
		familyMembers.add(createPerson(Gender.Female, "Mother", "Lastname", Arrays.asList(sedan, bicycle)));
		familyMembers.get(2).get_children().add(familyMembers.get(0));
		familyMembers.get(2).get_children().add(familyMembers.get(1));
		familyMembers.get(3).get_children().add(familyMembers.get(0));
		familyMembers.get(3).get_children().add(familyMembers.get(1));
		family1 = new Family(familyMembers);
	}

	private Person createPerson(Gender gender, String firstName, String lastName, List<Vehicle> vehicles) {
		Person person = gender == Gender.Male? 
				new MalePerson(firstName, lastName):
				new FemalePerson(firstName, lastName);
		person.setVehicles(vehicles);
		return person;
	}

	@After
	public void tearDown() throws Exception {
		family1 = null;
	}

	@Test
	public void test() throws Exception {
		ObjectMapper mapper = new ObjectMapper()
			.configure(SerializationFeature.INDENT_OUTPUT, true)
			.enableDefaultTyping();
		
		String familyStr = mapper.writerFor(new TypeReference<Family>(){})
				.writeValueAsString(family1);
		assertNotNull(familyStr);
		JSONAssert.assertEquals(_expectedJsonFamily, familyStr, false);
		
		@SuppressWarnings("unchecked")
		Family family = (Family)mapper.readValue(new StringReader(familyStr), new TypeReference<Family>(){});
		
		Person father = family.getFamilyMembers().get(2);
		assertEquals(2, father.get_children().size());
		assertEquals(Gender.Male, father.get_Gender());
		
		Person mother = family.getFamilyMembers().get(3);
		assertEquals(Gender.Female, mother.get_Gender());
		
		Person child1 = family.getFamilyMembers().get(0);
		Person childOfFatherChild1 = father.get_children().get(0);
		Person childOfMotherChild1 = mother.get_children().get(0);
		assertSame(child1, childOfFatherChild1);
		assertSame(child1, childOfMotherChild1);
		
		Person child2 = family.getFamilyMembers().get(1);
		Person childOfFatherChild2 = father.get_children().get(1);
		Person childOfMotherChild2 = mother.get_children().get(1);
		assertSame(child2, childOfFatherChild2);
		assertSame(child2, childOfMotherChild2);
		
		JsonNode familyNode = mapper.readTree(familyStr);
		String familyNodeString = familyNode.toString();
		
		JSONAssert.assertEquals(familyStr, familyNodeString, false);
	}
}
