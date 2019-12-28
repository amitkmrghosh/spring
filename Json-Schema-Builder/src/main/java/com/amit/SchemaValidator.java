package com.amit;

import java.io.File;
import java.io.IOException;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public class SchemaValidator {
	public static void main( String[] args ) throws IOException, ProcessingException
	{
	    File schemaFile = new File("/Users/amitkumarghosh/Downloads/spring_downloads/Json-Schema-Builder/src/main/resources/schema.json");
	    File jsonFile = new File("/Users/amitkumarghosh/Downloads/spring_downloads/Json-Schema-Builder/src/main/resources/data.json");
	    	
	    if (ValidationUtils.isJsonValid(schemaFile, jsonFile)){
	    	System.out.println("Valid!");
	    }else{
	    	System.out.println("NOT valid!");
	    }
	}
}
