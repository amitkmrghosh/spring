package com.example.springcamel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents an user of the system")
public class User {

	@ApiModelProperty(value = "The ID of the user", required = true)
    private Integer id;

	@ApiModelProperty(value = "The ID of the user", required = true)
    private String name;

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //@Override
    //public String toString() {
    //    return ToStringBuilder.reflectionToString(this);
    //}

}