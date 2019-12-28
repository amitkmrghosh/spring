package com.amit;

import java.util.List;

import com.github.reinert.jjschema.Attributes;
 
@Attributes(title = "Employee", description = "Schema for an employee")
public class Employee {
 
    @Attributes(required = true, description = "ID of the employee")
    private int id;
 
    @Attributes(required = true, minLength = 1, maxLength = 15, description = "First name of the employee")
    private String firstName;
 
    @Attributes(required = true, minLength = 1, maxLength = 15, description = "Last name of the employee")
    private String lastName;
 
    @Attributes(required = true, description = "Age in years of the employee")
    private int age;
 
    @Attributes(required = true, description = "Gender of the employee", enums = {"MALE", "FEMALE"})
    private String gender;
 
    @Attributes(required = true, minItems = 1, maxItems = 3, minLength = 1, maxLength = 30, description = "Address lines of the employee")
    private List<String> address;

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
 
}
