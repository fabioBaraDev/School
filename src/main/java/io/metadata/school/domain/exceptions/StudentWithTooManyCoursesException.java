package io.metadata.school.domain.exceptions;

public class StudentWithTooManyCoursesException extends Exception {

	private static final long serialVersionUID = 3050220221809622111L;
	
	public StudentWithTooManyCoursesException() {
		super("This student already has 5 courses");
	}
}
