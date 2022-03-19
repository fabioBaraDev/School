package io.metadata.school.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.metadata.school.domain.model.Course;

public class CourseDTO {

	
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
}
