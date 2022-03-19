package io.metadata.school.application.services.exception;

public class StudentAlreadyHasThisCourseException extends Exception {

	private static final long serialVersionUID = 3448700469872830835L;

	public StudentAlreadyHasThisCourseException() {
		super("Student already has this course, validate your data!");
	}
}
