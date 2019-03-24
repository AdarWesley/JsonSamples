package org.awesley;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonSerializatonTest {

	private Family family1;
	private String _expectedJsonFamily = 
			"{\r\n" + 
			"  \"_type\" : \"org.awesley.Family\",\r\n" + 
			"  \"@id\" : 1,\r\n" + 
			"  \"familyMembers\" : [ \"java.util.ArrayList\", [ {\r\n" + 
			"    \"_type\" : \"org.awesley.MalePerson\",\r\n" + 
			"    \"@id\" : 2,\r\n" + 
			"    \"_firstName\" : \"Child1\",\r\n" + 
			"    \"_lastName\" : \"Lastname\",\r\n" + 
			"    \"vehicles\" : [ \"java.util.ArrayList\", [ {\r\n" + 
			"      \"_type\" : \"org.awesley.Corcinet\",\r\n" + 
			"      \"@id\" : 3,\r\n" + 
			"      \"color\" : \"Red\"\r\n" + 
			"    }, {\r\n" + 
			"      \"_type\" : \"org.awesley.Bicycle\",\r\n" + 
			"      \"@id\" : 4,\r\n" + 
			"      \"color\" : \"Blue\"\r\n" + 
			"    } ] ],\r\n" + 
			"    \"_children\" : [ \"java.util.ArrayList\", [ ] ]\r\n" + 
			"  }, {\r\n" + 
			"    \"_type\" : \"org.awesley.FemalePerson\",\r\n" + 
			"    \"@id\" : 5,\r\n" + 
			"    \"_firstName\" : \"Child2\",\r\n" + 
			"    \"_lastName\" : \"Lastname\",\r\n" + 
			"    \"vehicles\" : [ \"java.util.ArrayList\", [ 4 ] ],\r\n" + 
			"    \"_children\" : [ \"java.util.ArrayList\", [ ] ]\r\n" + 
			"  }, {\r\n" + 
			"    \"_type\" : \"org.awesley.MalePerson\",\r\n" + 
			"    \"@id\" : 6,\r\n" + 
			"    \"_firstName\" : \"Father\",\r\n" + 
			"    \"_lastName\" : \"Lastname\",\r\n" + 
			"    \"vehicles\" : [ \"java.util.ArrayList\", [ {\r\n" + 
			"      \"_type\" : \"org.awesley.Jeep\",\r\n" + 
			"      \"@id\" : 7,\r\n" + 
			"      \"engineSize\" : 2000,\r\n" + 
			"      \"color\" : \"Black\"\r\n" + 
			"    }, {\r\n" + 
			"      \"_type\" : \"org.awesley.Sedan\",\r\n" + 
			"      \"@id\" : 8,\r\n" + 
			"      \"engineSize\" : 1600,\r\n" + 
			"      \"color\" : \"White\"\r\n" + 
			"    } ] ],\r\n" + 
			"    \"_children\" : [ \"java.util.ArrayList\", [ 2, 5 ] ]\r\n" + 
			"  }, {\r\n" + 
			"    \"_type\" : \"org.awesley.FemalePerson\",\r\n" + 
			"    \"@id\" : 9,\r\n" + 
			"    \"_firstName\" : \"Mother\",\r\n" + 
			"    \"_lastName\" : \"Lastname\",\r\n" + 
			"    \"vehicles\" : [ \"java.util.ArrayList\", [ 8, 4 ] ],\r\n" + 
			"    \"_children\" : [ \"java.util.ArrayList\", [ 2, 5 ] ]\r\n" + 
			"  } ] ]\r\n" + 
			"}";
	
	@Before
	public void setUp() throws Exception {
		family1 = createFamily();
	}

	@After
	public void tearDown() throws Exception {
		family1 = null;
	}

	@Test
	public void shouldSerializeToJSONandBackWithPolymorphicChilrenLists() throws Exception {
        ObjectMapper mapper = new ObjectMapper()
			.configure(SerializationFeature.INDENT_OUTPUT, true)
			.enableDefaultTyping(DefaultTyping.NON_FINAL, As.WRAPPER_OBJECT)
//			.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
//				@Override
//				public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
//				    if (Modifier.isAbstract(baseType.getRawClass().getModifiers())){
//				        StdTypeResolverBuilder typeResolverBuilder = new StdTypeResolverBuilder();
//				        typeResolverBuilder.init(Id.CLASS, null);
//				        typeResolverBuilder.typeProperty("_type");
//				        typeResolverBuilder.inclusion(As.EXTERNAL_PROPERTY);
//				        return typeResolverBuilder;
//				    }
//				    return super.findTypeResolver(config, ac, baseType);
//				}
//			})
			.addMixIn(Object.class, ObjectMixIn.class)
			;
		
		String familyStr = mapper.writeValueAsString(family1);
		assertNotNull(familyStr);
		JSONAssert.assertEquals(_expectedJsonFamily, familyStr, false);
		
		Family family = (Family)mapper.readValue(new StringReader(familyStr), new TypeReference<Family>(){});
		
		validateFamily(family1, family);
		
	}
	
	@Test
	public void shouldDeserializeJsonStringToJsonNode() throws Exception {
		ObjectMapper jsonNodeMapper = new ObjectMapper()
				.configure(SerializationFeature.INDENT_OUTPUT, true);
		JsonNode familyNode = jsonNodeMapper.readTree(_expectedJsonFamily); 
		String familyNodeString = familyNode.toString();
		
		JSONAssert.assertEquals(_expectedJsonFamily, familyNodeString, false);
	}

	private Family createFamily() {
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
		Family newFamily = new Family(familyMembers);
		return newFamily;
	}

	private Person createPerson(Gender gender, String firstName, String lastName, List<Vehicle> vehicles) {
		Person person = gender == Gender.Male? 
				new MalePerson(firstName, lastName):
				new FemalePerson(firstName, lastName);
		person.setVehicles(vehicles);
		return person;
	}

	private void validateFamily(Family originalFamily, Family family) {
		Vehicle jeep = new Jeep(2000, "Black");
		Vehicle sedan = new Sedan(1600, "White");
		Vehicle corcinet = new Corcinet("Red");
		Vehicle bicycle = new Bicycle("Blue");

		Person father = family.getFamilyMembers().get(2);
		validatePerson(father, 2, 2, Gender.Male, "Father", "Lastname", originalFamily.getFamilyMembers().get(2).get_children(), Arrays.asList(jeep, sedan));
		
		Person mother = family.getFamilyMembers().get(3);
		validatePerson(mother, 2, 2, Gender.Female, "Mother", "Lastname", originalFamily.getFamilyMembers().get(3).get_children(), Arrays.asList(sedan, bicycle));

		Person child1 = family.getFamilyMembers().get(0);
		validatePerson(child1, 0, 2, Gender.Male, "Child1", "Lastname", originalFamily.getFamilyMembers().get(0).get_children(), Arrays.asList(corcinet, bicycle));
		
		Person child2 = family.getFamilyMembers().get(1);
		validatePerson(child2, 0, 1, Gender.Female, "Child2", "Lastname", originalFamily.getFamilyMembers().get(2).get_children(), Arrays.asList(bicycle));

		Person childOfFatherChild1 = father.get_children().get(0);
		Person childOfMotherChild1 = mother.get_children().get(0);
		assertSame(child1, childOfFatherChild1);
		assertSame(child1, childOfMotherChild1);
		
		Person childOfFatherChild2 = father.get_children().get(1);
		Person childOfMotherChild2 = mother.get_children().get(1);
		assertSame(child2, childOfFatherChild2);
		assertSame(child2, childOfMotherChild2);
		
		assertSame(child1.getVehicles().get(1), child2.getVehicles().get(0));
		assertSame(child1.getVehicles().get(1), mother.getVehicles().get(1));
		assertSame(father.getVehicles().get(1), mother.getVehicles().get(0));
	}
	
	private void validatePerson(Person person, int numChildren, int numVehicles, Gender gender, String firstName, String lastName, List<Person> children, List<Vehicle> vehicles) {
		assertEquals(gender, person.get_Gender());
		assertEquals(firstName, person.get_firstName());
		assertEquals(lastName, person.get_lastName());
		assertEquals(numChildren, person.get_children().size());
		for (int i = 0; i < numChildren; i++) {
			assertEquals(children.get(i), person.get_children().get(i));
		}
		assertEquals(numVehicles, person.getVehicles().size());
		for (int i = 0; i < numVehicles; i++) {
			assertEquals(vehicles.get(i), person.getVehicles().get(i));
		}
	}
}
