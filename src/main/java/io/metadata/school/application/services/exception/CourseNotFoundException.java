package io.metadata.school.application.services.exception;

public class CourseNotFoundException extends Exception {

	private static final long serialVersionUID = 5135518765702111385L;

	public CourseNotFoundException() {
		super("No data to delete!");
	}
}
