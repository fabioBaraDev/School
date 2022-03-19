package io.metadata.school.application.services.exception;

public class StudentNotFoundException extends Exception {

	private static final long serialVersionUID = 3448700469872830835L;

	public StudentNotFoundException() {
		super("Student already has this course, validate your data!");
	}
}
