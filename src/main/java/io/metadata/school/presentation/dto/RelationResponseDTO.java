package io.metadata.school.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.metadata.school.domain.model.Course;
import io.metadata.school.domain.model.Student;

public class RelationResponseDTO {

	@JsonProperty("student_id")
	private Integer studentId;

	@JsonProperty("student_name")
	private String studentName;
	
	@JsonProperty("course_id")
	private Integer courseId;
	
	@JsonProperty("course_name")
	private String courseName;
	
	public RelationResponseDTO() {}
	
	public RelationResponseDTO(Integer studentId, Integer courseId) {
		this.studentId = studentId;
		this.courseId = courseId;
	}
	
	public RelationResponseDTO(Student student, Course course) {
		this.studentId = student.getId();
		this.studentName = student.getName();
		this.courseId = course.getId();
		this.courseName = course.getName();
	}

	public Integer getStudentId() {
		return studentId;
	}

	public Integer getCourseId() {
		return courseId;
	}
}
