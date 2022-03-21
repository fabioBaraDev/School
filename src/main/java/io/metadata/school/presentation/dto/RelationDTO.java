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

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();
		String doubleQuotes = "\"";

		str.append("{").append("\n").append(doubleQuotes).append("student_id").append(doubleQuotes).append(": ")
				.append(doubleQuotes).append(this.getStudentId().toString()).append(doubleQuotes).append(",\n")
				.append(doubleQuotes).append("course_id").append(doubleQuotes).append(": ")
				.append(doubleQuotes).append(this.getCourseId().toString()).append(doubleQuotes).append("\n")
				.append("}");

		return str.toString();
	}
}
