package io.metadata.school.domain.exceptions;

public class CourseDataNotProvidedException extends Exception {

	private static final long serialVersionUID = -650350624096687842L;

	public CourseDataNotProvidedException() {
		super("Course data not provided");
	}
}
