package io.metadata.school.presentation.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.metadata.school.domain.model.Course;

public class CourseDTO implements Serializable {

	private static final long serialVersionUID = 7694477334714595879L;

	@JsonProperty("name")
	private String name;

	public CourseDTO() {
	}

	public CourseDTO(Integer id, String name) {

		this.setName(name);
	}

	public CourseDTO(Course course) {
		this.setName(course.getName());
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
