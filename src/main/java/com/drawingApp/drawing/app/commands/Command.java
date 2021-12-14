package com.drawingApp.drawing.app.commands;

import com.drawingApp.drawing.app.exceptions.InvalidInputException;

public interface Command {
    /**
     * Execute command.
     * @throws InvalidInputException Exception on Invalid user's input.
     */
    void execute() throws InvalidInputException;

}
