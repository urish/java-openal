package org.urish.openal;

public class ALException extends Exception {
	private static final long serialVersionUID = 1L;

	public ALException() {
		super();
	}

	public ALException(String message, Throwable cause) {
		super(message, cause);
	}

	public ALException(String message) {
		super(message);
	}

	public ALException(Throwable cause) {
		super(cause);
	}

}
