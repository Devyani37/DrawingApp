package com.drawingApp.drawing.app.commands;

import com.drawingApp.drawing.app.exceptions.InvalidInputException;
import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;

/**
 * Command to fill the Bucket with given color character.
 */
public class BucketFill implements Command {

    private final int x;
    private final int y;
    private final Character color;
    private final InMemoryCanvasRepository inMemoryCanvasRepository;

    public BucketFill(int x, int y, Character color, InMemoryCanvasRepository inMemoryCanvasRepository) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.inMemoryCanvasRepository = inMemoryCanvasRepository;
    }


    @Override
    public void execute() throws InvalidInputException {
        inMemoryCanvasRepository.bucketFill(x, y, color);
    }
}
