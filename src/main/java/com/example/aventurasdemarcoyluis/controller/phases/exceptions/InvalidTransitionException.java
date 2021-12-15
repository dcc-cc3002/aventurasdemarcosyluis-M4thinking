package com.example.aventurasdemarcoyluis.controller.phases.exceptions;

public class InvalidTransitionException extends Exception {

	/**
	 * <p>
	 *     Exception thrown when going from
	 *     one phase to another incorrectly,
	 *     violating the state diagram.
	 * </p>
	 * @param message Phrase that best describes the error
	 */
	public InvalidTransitionException(final String message){
		super(message);
	}
}
