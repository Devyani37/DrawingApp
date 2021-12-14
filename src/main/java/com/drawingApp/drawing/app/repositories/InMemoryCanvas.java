package com.drawingApp.drawing.app.repositories;

/**
 * To represent a Canvas in memory.
 */
public class InMemoryCanvas {
    /**
     * Initially an empty canvas.
     */
    private char[][] canvas = new char[0][0];

    /**
     * Save or update canvas.
     * @param updatedCanvas Canvas to be updated.
     * @return updated canvas.
     */
    public char[][] save(char[][] updatedCanvas) {
        canvas = updatedCanvas;
        return canvas;
    }

    /**
     * Gets existing inMemory Canvas.
     *
     * @return in memory canvas.
     */
    public char[][] getCanvas() {
        return canvas;
    }
}
