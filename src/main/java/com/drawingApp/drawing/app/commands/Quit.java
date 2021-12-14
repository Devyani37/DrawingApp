package com.drawingApp.drawing.app.commands;

/**
 * Command to Quit the Program.
 */
public class Quit implements Command {
    @Override
    public void execute() {
        System.out.println("Shutting down the program.. Bye!");
        System.exit(0);
    }
}
