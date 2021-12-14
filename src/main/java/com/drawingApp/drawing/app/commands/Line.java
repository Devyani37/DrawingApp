package com.drawingApp.drawing.app.commands;


import com.drawingApp.drawing.app.exceptions.InvalidInputException;
import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;

/**
 * Command to draw a Line in Canvas.
 */
public class Line implements Command {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final InMemoryCanvasRepository inMemoryCanvasRepository;


    public Line(int x1, int y1, int x2, int y2, InMemoryCanvasRepository inMemoryCanvasRepository) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.inMemoryCanvasRepository = inMemoryCanvasRepository;
    }


    @Override
    public void execute() throws InvalidInputException {
        inMemoryCanvasRepository.drawLine(x1, y1, x2, y2);
    }
}
