package com.drawingApp.drawing.app;


import com.drawingApp.drawing.app.commands.CommandFactory;
import com.drawingApp.drawing.app.exceptions.InvalidInputException;
import com.drawingApp.drawing.app.repositories.InMemoryCanvas;
import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;
import com.drawingApp.drawing.app.validators.InputCommandValidator;

import java.util.Scanner;


public class DrawingAppApplication {

    public static void main(String[] args) {
        displayCatalogue();

        var validator = new InputCommandValidator();
        var inMemoryCanvas = new InMemoryCanvas();
        var canvasRepository = new InMemoryCanvasRepository(inMemoryCanvas);
        var commandFactory = new CommandFactory(canvasRepository);

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Enter your command here :  ");

                var inputCommand = scanner.nextLine();

                if (validator.isValid(inputCommand)) {
                    try {
                        var command = commandFactory.getCommand(inputCommand);
                        // execute command.
                        command.execute();
                        // display latest canvas.
                        displayCanvas(inMemoryCanvas.getCanvas());

                    } catch (InvalidInputException e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.err.println("Input Command is not valid, Please check Catalogue.");
                    displayCatalogue();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display Command Catalogue on STDOUT.
     */
    private static void displayCatalogue() {
        StringBuilder catalogue = new StringBuilder("CATALOGUE");
        catalogue.append(System.lineSeparator());
        catalogue.append("The program should work as follows:");
        catalogue.append(System.lineSeparator());
        catalogue.append("1. Create a new canvas ");
        catalogue.append(System.lineSeparator());
        catalogue.append("2. Start drawing on the canvas by issuing various commands");
        catalogue.append(System.lineSeparator());
        catalogue.append("3. Quit");
        catalogue.append(System.lineSeparator());
        catalogue.append(System.lineSeparator());
        catalogue.append("Command 		Description");
        catalogue.append(System.lineSeparator());
        catalogue.append("C w h           Should create a new canvas of width w and height h.");
        catalogue.append(System.lineSeparator());
        catalogue.append("L x1 y1 x2 y2   Should create a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are supported. Horizontal and vertical lines will be drawn using the 'x' character.");
        catalogue.append(System.lineSeparator());
        catalogue.append("R x1 y1 x2 y2   Should create a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2). Horizontal and vertical lines will be drawn using the 'x' character.");
        catalogue.append(System.lineSeparator());
        catalogue.append("B x y c         Should fill the entire area connected to (x,y) with 'colour' c. The behaviour of this is the same as that of the 'bucket fill' tool in paint programs.");
        catalogue.append(System.lineSeparator());
        catalogue.append("Q               Should quit the program.");

        System.out.println(catalogue);
    }

    /**
     * Display canvas on STDOUT.
     * @param canvas Canvas to display.
     */
    private static void displayCanvas(char[][] canvas) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[i].length; j++) {
                if (canvas[i][j] == 0) {
                    output.append(" ");
                } else {
                    output.append(canvas[i][j]);
                }
            }
            output.append(System.lineSeparator());
        }
        System.out.println(output);
    }

}
