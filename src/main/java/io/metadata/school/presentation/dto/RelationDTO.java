package io.metadata.school.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelationDTO {

	@JsonProperty("student_id")
	private Integer studentId;
	
	@JsonProperty("course_id")
	private Integer courseId;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
}
