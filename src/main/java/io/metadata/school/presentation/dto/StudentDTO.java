package io.metadata.school.presentation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.metadata.school.domain.model.Student;

public class StudentDTO {

	@NotBlank @NotEmpty @NotNull
	private String name;

	public StudentDTO() {
	}

	public StudentDTO(String name) {
		this.setName(name);
	}
	
	public StudentDTO(Student student) {
		this.setName(student.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();
		String doubleQuotes = "\"";

		str.append("{").append("\n").append(doubleQuotes).append("name").append(doubleQuotes).append(": ").append(doubleQuotes).append(this.getName().toString())
				.append(doubleQuotes).append("\n").append("}");

		return str.toString();
	}
}
