package com.example.aventurasdemarcoyluis.controller.phases.exceptions;

public class InvalidMovementException extends Exception {
	/**
	 * Invalid movement exception for correct phase transition
	 * @param message Phrase that best describes the error
	 */
	public InvalidMovementException(final String message){
		super(message);
	}
}
