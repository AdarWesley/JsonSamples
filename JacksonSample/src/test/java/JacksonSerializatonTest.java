import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonSerializatonTest {

	private ArrayList<Person> _family;
	private String _expectedJsonFamily = 
			"[ {" +
			"	  \"@class\" : \"MalePerson\"," +
			"	  \"@id\" : 1," +
			"	  \"_firstName\" : \"Child1\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"_children\" : [ ]" +
			"	}, {" +
			"	  \"@class\" : \"FemalePerson\"," +
			"	  \"@id\" : 2," +
			"	  \"_firstName\" : \"Child2\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"_children\" : [ ]" +
			"	}, {" +
			"	  \"@class\" : \"MalePerson\"," +
			"	  \"@id\" : 3," +
			"	  \"_firstName\" : \"Father\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"_children\" : [ 1, 2 ]" +
			"	}, {" +
			"	  \"@class\" : \"FemalePerson\"," +
			"	  \"@id\" : 4," +
			"	  \"_firstName\" : \"Mother\"," +
			"	  \"_lastName\" : \"Lastname\"," +
			"	  \"_children\" : [ 1, 2 ]" +
			"	} ]";
	
	@Before
	public void setUp() throws Exception {
		_family = new ArrayList<Person>();
		_family.add(new MalePerson("Child1", "Lastname"));
		_family.add(new FemalePerson("Child2", "Lastname"));
		_family.add(new MalePerson("Father", "Lastname"));
		_family.add(new FemalePerson("Mother", "Lastname"));
		_family.get(2).get_children().add(_family.get(0));
		_family.get(2).get_children().add(_family.get(1));
		_family.get(3).get_children().add(_family.get(0));
		_family.get(3).get_children().add(_family.get(1));
	}

	@After
	public void tearDown() throws Exception {
		_family = null;
	}

	@Test
	public void test() throws Exception {
		ObjectMapper mapper = new ObjectMapper()
			.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		String familyStr = mapper.writerFor(new TypeReference<ArrayList<Person>>(){})
				.writeValueAsString(_family);
		assertNotNull(familyStr);
		JSONAssert.assertEquals(_expectedJsonFamily, familyStr, false);
		
		@SuppressWarnings("unchecked")
		ArrayList<Person> family = 
				(ArrayList<Person>)mapper.readValue(new StringReader(familyStr), new TypeReference<ArrayList<Person>>(){});
		
		Person father = family.get(2);
		assertEquals(2, father.get_children().size());
		assertEquals(Gender.Male, father.get_Gender());
		
		Person mother = family.get(3);
		assertEquals(Gender.Female, mother.get_Gender());
		
		Person child1 = family.get(0);
		Person childOfFatherChild1 = father.get_children().get(0);
		Person childOfMotherChild1 = mother.get_children().get(0);
		assertSame(child1, childOfFatherChild1);
		assertSame(child1, childOfMotherChild1);
		
		Person inbal = family.get(1);
		Person childOfFatherChild2 = father.get_children().get(1);
		Person childOfMotherChild2 = mother.get_children().get(1);
		assertSame(inbal, childOfFatherChild2);
		assertSame(inbal, childOfMotherChild2);
		
		JsonNode familyNode = mapper.readTree(familyStr);
		String familyNodeString = familyNode.toString();
		
		JSONAssert.assertEquals(familyStr, familyNodeString, false);
	}
}
