package com.amit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.SchemaGeneratorBuilder.ConfigurationStep;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
 
public class SchemaGeneratorTest {
    private static ObjectMapper mapper = new ObjectMapper();
    public static final String JSON_$SCHEMA_DRAFT4_VALUE = "http://json-schema.org/draft-04/schema#";
    public static final String JSON_$SCHEMA_ELEMENT = "$schema";
    
    static {
        // required for pretty printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public static void main(String[] args) throws JsonProcessingException {
        
        JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();
        schemaFactory.setAutoPutDollarSchema(true);
        JsonNode schemaNode = schemaFactory.createSchema(Employee.class);
        prettyPrintSchema(schemaNode);
    }
    
    private static void prettyPrintSchema(JsonNode schema) throws JsonProcessingException{
        System.out.println(mapper.writeValueAsString(schema));
    }
}
