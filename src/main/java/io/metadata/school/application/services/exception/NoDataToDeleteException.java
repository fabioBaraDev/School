package io.metadata.school.application.services.exception;

public class NoDataToDeleteException extends Exception {

	private static final long serialVersionUID = 5135518765702111385L;

	public NoDataToDeleteException() {
		super("Found no data to delete, validate your data!");
	}
}
