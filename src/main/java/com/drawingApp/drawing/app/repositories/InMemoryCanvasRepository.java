package com.drawingApp.drawing.app.repositories;


import com.drawingApp.drawing.app.exceptions.InvalidInputException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Contains various in memory canvas operations.
 */
public class InMemoryCanvasRepository {
    /**
     * In-memory canvas store.
     */
    private final InMemoryCanvas inMemoryCanvas;
    /**
     * Character to represent a line on canvas.
     */
    private final Character CHAR_X = 'x';


    public InMemoryCanvasRepository(InMemoryCanvas inMemoryCanvas) {
        this.inMemoryCanvas = inMemoryCanvas;
    }

    /**
     * Create new Canvas.
     *
     * @param width  Width of Canvas.
     * @param height Height of Canvas.
     * @throws InvalidInputException if height or width equals to 0.
     */
    public void newCanvas(int width, int height) throws InvalidInputException {
        var borderHorizontal = '-';
        var borderVertical = '|';

        if (height <= 0 || width <= 0) {
            throw new InvalidInputException("Height or Width of Canvas must be greater than 0");
        }
        // Two extra rows and columns are added for Canvas's border.
        char[][] newCanvas = new char[height + 2][width + 2];

        // Top Border of canvas.
        Arrays.fill(newCanvas[0], borderHorizontal);
        // Bottom border of canvas.
        Arrays.fill(newCanvas[newCanvas.length - 1], borderHorizontal);
        //Left border of canvas.
        for (int i = 1; i <= newCanvas.length - 2; i++) {
            newCanvas[i][0] = borderVertical;
        }
        //Right border of canvas.
        for (int i = 1; i <= newCanvas.length - 2; i++) {
            newCanvas[i][newCanvas[i].length - 1] = borderVertical;
        }

        inMemoryCanvas.save(newCanvas);
    }

    /**
     * Draw a Line in existing Canvas. Only Horizontal and Vertical lines are supported.
     *
     * @param x1 Starts from coordinate x1.
     * @param y1 Starts from coordinate y1.
     * @param x2 Ends on coordinate x2.
     * @param y2 Ends on coordinate y2.
     * @throws InvalidInputException if input coordinates are beyond canvas borders.
     */
    public void drawLine(int x1, int y1, int x2, int y2) throws InvalidInputException {
        //Check if we already have a canvas.
        isCanvasExist();

        var canvasToDraw = inMemoryCanvas.getCanvas();
        if (isValidCoordinates(canvasToDraw, y1, x1) && isValidCoordinates(canvasToDraw, y2, x2)) {

            if (x1 > x2 || y1 > y2 || (x1 == x2 && y1 == y2)) {
                throw new InvalidInputException("Invalid inputs. x1 and y1 must be less than x2 and y2 respectively.");
            }

            if (y1 == y2) {  //Horizontal Line
                for (int i = x1; i <= x2; i++) {
                    canvasToDraw[y1][i] = CHAR_X;
                }
            } else if (x1 == x2) { //Vertical Line
                for (int j = y1; j <= y2; j++) {
                    canvasToDraw[j][x1] = CHAR_X;
                }
            } else {
                throw new InvalidInputException("Invalid inputs .Only horizontal and vertical lines are supported.");
            }

            inMemoryCanvas.save(canvasToDraw);

        } else {
            throw new InvalidInputException("Invalid inputs. Coordinates are beyond canvas borders.");
        }
    }

    /**
     * Draw a Rectangle in existing Canvas.
     *
     * @param x1 Top left corner's x coordinate.
     * @param y1 Top left corner's y coordinate.
     * @param x2 Bottom right corner's x coordinate.
     * @param y2 Bottom right corner's y coordinate.
     * @throws InvalidInputException If Coordinates are beyond canvas borders.
     */
    public void drawRectangle(int x1, int y1, int x2, int y2) throws InvalidInputException {
        isCanvasExist();

        var canvasToDraw = inMemoryCanvas.getCanvas();
        if (isValidCoordinates(canvasToDraw, y1, x1) && isValidCoordinates(canvasToDraw, y2, x2)) {

            if (x1 >= x2 || y1 >= y2) {
                throw new InvalidInputException("Invalid inputs. x1 and y1 must be less than x2 and y2 respectively.");
            }
            //Upper & Lower Border
            for (int i = x1; i <= x2; i++) {
                canvasToDraw[y1][i] = CHAR_X;
                canvasToDraw[y2][i] = CHAR_X;
            }
            //Left & Right Border
            for (int i = y1; i <= y2; i++) {
                canvasToDraw[i][x1] = CHAR_X;
                canvasToDraw[i][x2] = CHAR_X;
            }

            inMemoryCanvas.save(canvasToDraw);

        } else {
            throw new InvalidInputException("Invalid inputs. Coordinates are beyond canvas borders.");
        }
    }

    /**
     * Fill an existing Canvas with given color's character.
     *
     * @param x     Starts from coordinate x.
     * @param y     Starts from coordinate y.
     * @param color character to fill the canvas.
     * @throws InvalidInputException if color character is 'x'.
     */
    public void bucketFill(int x, int y, Character color) throws InvalidInputException {
        isCanvasExist();

        if (color == CHAR_X) {
            throw new InvalidInputException("Invalid input. Color must not be " + CHAR_X);
        }
        var canvasToDraw = inMemoryCanvas.getCanvas();
        if (isValidCoordinates(canvasToDraw, y, x)) {
            // canvas[y][x] must not part of any existing line.
            if (canvasToDraw[y][x] != CHAR_X) {
                fillCanvas(canvasToDraw, x, y, color);
                inMemoryCanvas.save(canvasToDraw);
            }

        } else {
            throw new InvalidInputException("Invalid inputs. Coordinates are beyond canvas borders.");
        }
    }

    /**
     * Validate if canvas is available for drawing.
     *
     * @throws InvalidInputException if Canvas is not initialize.
     */
    private void isCanvasExist() throws InvalidInputException {
        if (inMemoryCanvas.getCanvas().length == 0) {
            throw new InvalidInputException("Canvas Not Found. Please create canvas first.");
        }
    }

    /**
     * Validate if given row and col are inside the canvas's borders
     *
     * @param canvas Canvas to draw upon.
     * @param row    Row to validate.
     * @param col    Column to validate.
     * @return TRUE if row and column are within the drawing area otherwise FALSE.
     */
    private boolean isValidCoordinates(char[][] canvas, int row, int col) {
        return row >= 1 && row < canvas.length - 1
                && col >= 1 && col < canvas[row].length - 1;
    }

    /**
     * Helper method to fill canvas using BFS (Breadth First Search).
     *
     * @param canvasToDraw Canvas to draw upon.
     * @param x            Starts from coordinate x.
     * @param y            Starts from coordinate y.
     * @param color        character to fill the canvas.
     */
    private void fillCanvas(char[][] canvasToDraw, int x, int y, Character color) {
        //To keep track of visited coordinates.
        boolean[][] visited = new boolean[canvasToDraw.length][canvasToDraw[0].length];

        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(List.of(y, x));
        visited[y][x] = true;

        int[] xDirection = {-1, 0, 1, 0};
        int[] yDirection = {0, 1, 0, -1};

        while (!queue.isEmpty()) {
            var item = queue.remove();
            var xCoordinate = item.get(0);
            var yCoordinate = item.get(1);

            //Fill the color.
            canvasToDraw[xCoordinate][yCoordinate] = color;

            for (int i = 0; i < 4; i++) {
                var adjacentX = xCoordinate + xDirection[i];
                var adjacentY = yCoordinate + yDirection[i];

                if (isValidCoordinates(canvasToDraw, adjacentX, adjacentY)
                        && !visited[adjacentX][adjacentY]
                        && canvasToDraw[adjacentX][adjacentY] != CHAR_X) {

                    queue.add(List.of(adjacentX, adjacentY));
                    visited[adjacentX][adjacentY] = true;
                }
            }
        }
    }

}
