package io.metadata.school.domain.exceptions;

public class TooManyException extends Exception {

	private static final long serialVersionUID = 3050220221809622111L;
	
	public TooManyException() {
		super("This student has too many courses or the course are full");
	}
}
