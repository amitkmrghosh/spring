package com.amit;



import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@DynamicInsert
@DynamicUpdate
public class SkippedProduct implements Serializable {

	@Id
	@GeneratedValue
	public Long id;

	public Integer lineNumber;

	public String input;

	@Override
	public String toString() {
		return "SkippedProduct# lineNumber=" + lineNumber + ", input=" + input;
	}

	private static final long serialVersionUID = 2462868704626608656L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}
