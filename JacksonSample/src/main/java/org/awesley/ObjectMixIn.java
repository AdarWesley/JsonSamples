package org.awesley;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonTypeInfo(use = Id.CLASS, property = "_type", include = As.EXTERNAL_PROPERTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ObjectMixIn {
}
