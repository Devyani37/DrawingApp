package com.drawingApp.drawing.app.exceptions;

/**
 * Custom Exception to represent an invalid inputs from user.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message){
        super(message);
    }
}
