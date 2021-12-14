package com.drawingApp.drawing.app.commands;

import com.drawingApp.drawing.app.exceptions.InvalidInputException;
import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;

/**
 * Command to create a new Canvas.
 */
public class Canvas implements Command {

    private final int width;
    private final int height;
    private final InMemoryCanvasRepository inMemoryCanvasRepository;

    public Canvas(int width, int height, InMemoryCanvasRepository inMemoryCanvasRepository) {
        this.width = width;
        this.height = height;
        this.inMemoryCanvasRepository = inMemoryCanvasRepository;
    }

    @Override
    public void execute() throws InvalidInputException {
        inMemoryCanvasRepository.newCanvas(width, height);
    }
}
