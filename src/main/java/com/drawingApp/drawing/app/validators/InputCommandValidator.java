package com.drawingApp.drawing.app.validators;

import com.drawingApp.drawing.app.commands.CommandType;

import java.util.Arrays;

/**
 * To validate user's inputs.
 */
public class InputCommandValidator {
    /**
     * Determine if input command is valid or not.
     * @param inputCommand Command entered by user.
     * @return TRUE if input matches a command regex in {@link CommandType} otherwise FALSE.
     */
    public boolean isValid(String inputCommand) {

        return Arrays.stream(CommandType.values())
                .anyMatch(cmd -> cmd.getPattern().matcher(inputCommand).matches());
    }

}
