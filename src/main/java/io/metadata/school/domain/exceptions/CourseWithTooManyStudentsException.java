package io.metadata.school.domain.exceptions;

public class CourseWithTooManyStudentsException extends Exception {

	private static final long serialVersionUID = -8261362053595080556L;

	public CourseWithTooManyStudentsException() {
		super("This course are full");
	}
}
