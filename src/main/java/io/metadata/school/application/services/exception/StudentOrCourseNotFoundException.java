package io.metadata.school.application.services.exception;

public class StudentOrCourseNotFoundException extends Exception {

	private static final long serialVersionUID = 3448700469872830835L;

	public StudentOrCourseNotFoundException() {
		super("Student or Course not found, validate your data!");
	}
}
