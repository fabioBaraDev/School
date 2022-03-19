package io.metadata.school.domain.exceptions;

public class StudentDataNotProvidedException extends Exception {

	private static final long serialVersionUID = -4854292843103505233L;

	public StudentDataNotProvidedException() {
		super("Student data not provided");
	}
}
