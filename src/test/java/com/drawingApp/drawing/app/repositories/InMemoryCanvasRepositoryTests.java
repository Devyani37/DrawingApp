package com.drawingApp.drawing.app.repositories;


import com.drawingApp.drawing.app.exceptions.InvalidInputException;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class InMemoryCanvasRepositoryTests {

    @Mock
    InMemoryCanvas mockInMemoryCanvas;

    @InjectMocks
    InMemoryCanvasRepository sut;

    Faker faker = new Faker();

    char[][] emptyCanvas = new char[0][0];

    @Test
    public void newCanvasCreated_Success() throws InvalidInputException {

        //Arrange
        var canvasWidth = 20;
        var canvasHeight = 4;
        var expectedCanvas = getBlankCanvas(canvasWidth, canvasHeight);

        //Act
        sut.newCanvas(canvasWidth, canvasHeight);

        //Assert
        Mockito.verify(mockInMemoryCanvas).save(Mockito.eq(expectedCanvas));
    }

    @Test
    public void newCanvas_NonPositiveDimension_Failure() {

        assertThatThrownBy(() -> {
            sut.newCanvas(faker.number().numberBetween(Integer.MIN_VALUE, 0),
                    faker.number().numberBetween(Integer.MIN_VALUE, 0));
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage("Height or Width of Canvas must be greater than 0");

    }

    @Test
    public void drawLine_CanvasNotExist_Failure() {
        //Arrange
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(emptyCanvas);
        //Assert
        assertThatThrownBy(() -> {
            sut.drawLine(faker.number().randomDigitNotZero(),
                    faker.number().randomDigitNotZero(),
                    faker.number().randomDigitNotZero(),
                    faker.number().randomDigitNotZero());
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage("Canvas Not Found. Please create canvas first.");

    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("provideInvalidCoordinates")
    public void drawLine_InvalidCoordinates_Failure(String testName, int x1, int y1, int x2, int y2, String expectedError) throws InvalidInputException {
        var canvasWidth = 20;
        var canvasHeight = 4;
        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);

        assertThatThrownBy(() -> {
            sut.drawLine(x1, y1, x2, y2);
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage(expectedError);
    }

    @Test
    public void drawLine_WithSlope_Failure() {
        //Arrange
        var canvasWidth = 20;
        var canvasHeight = 4;
        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);

        //Act, Assert
        assertThatThrownBy(() -> {
            sut.drawLine(1, 2, 5, 3);
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid inputs .Only horizontal and vertical lines are supported.");
    }

    @Test
    public void drawLine_HorizontalLine_Success() throws InvalidInputException {
        //Arrange
        var canvasWidth = 20;
        var canvasHeight = 4;
        var x2 = 18;
        var x1 = 5;
        var y = 2;

        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);

        var expectedCanvas = drawLine(canvasToDraw, x1, y, x2, y);
        //Act
        sut.drawLine(x1, y, x2, y);

        // Assert
        Mockito.verify(mockInMemoryCanvas).save(Mockito.eq(expectedCanvas));
    }

    @Test
    public void drawLine_VerticalLine_Success() throws InvalidInputException {
        //Arrange
        var canvasWidth = 20;
        var canvasHeight = 6;
        var y2 = 4;
        var y1 = 1;
        var x = 16;

        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);


        var expectedCanvas = drawLine(canvasToDraw, x, y1, x, y2);

        //Act
        sut.drawLine(x, y1, x, y2);

        //Arrange
        Mockito.verify(mockInMemoryCanvas).save(Mockito.eq(expectedCanvas));

    }

    @Test
    public void drawRectangle_Success() throws InvalidInputException {
        //Arrange
        var canvasWidth = 24;
        var canvasHeight = 8;
        var x2 = 18;
        var x1 = 10;
        var y2 = 6;
        var y1 = 3;

        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);
        var expectedCanvas = drawRectangle(canvasToDraw, x1, y1, x2, y2);

        //Act
        sut.drawRectangle(x1, y1, x2, y2);

        //Assert
        Mockito.verify(mockInMemoryCanvas).save(Mockito.eq(expectedCanvas));

    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("provideInvalidCoordinates")
    public void drawRectangle_InvalidCoordinates_Failure(String testName, int x1, int y1, int x2, int y2, String expectedError) throws InvalidInputException {
        //Arrange
        var canvasWidth = 20;
        var canvasHeight = 4;
        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);

        //Act , Assert
        assertThatThrownBy(() -> {
            sut.drawRectangle(x1, y1, x2, y2);
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage(expectedError);
    }

    @Test
    public void bucketFill_Success() throws InvalidInputException {
        //Arrange
        var canvasWidth = 10;
        var canvasHeight = 4;
        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);
        var x = 6;
        var y = 3;
        var color = 'o';
        char[][] expectedCanvas = {{'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'|', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', '|'},
                {'|', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', '|'},
                {'|', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', '|'},
                {'|', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', '|'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'}};

        //Act
        sut.bucketFill(x, y, color);

        //Assert
        Mockito.verify(mockInMemoryCanvas).save(Mockito.eq(expectedCanvas));
    }

    @Test
    public void bucketFill_ExcludeExistingRectangle_Success() throws InvalidInputException {
        //Arrange
        var canvasWidth = 10;
        var canvasHeight = 4;
        var canvasToDraw = drawRectangle(getBlankCanvas(canvasWidth, canvasHeight), 3, 1, 7, 4);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);
        char[][] expectedCanvas = {{'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'|', 0, 0, 'x', 'x', 'x', 'x', 'x', 0, 0, 0, '|'},
                {'|', 0, 0, 'x', 'o', 'o', 'o', 'x', 0, 0, 0, '|'},
                {'|', 0, 0, 'x', 'o', 'o', 'o', 'x', 0, 0, 0, '|'},
                {'|', 0, 0, 'x', 'x', 'x', 'x', 'x', 0, 0, 0, '|'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'}};
        //Act
        sut.bucketFill(6, 3, 'o');
        //Assert
        Mockito.verify(mockInMemoryCanvas).save(Mockito.eq(expectedCanvas));
    }

    @Test
    public void bucketFill_CanvasNotExist_Failure() {
        //Arrange
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(emptyCanvas);
        //Act , Assert
        assertThatThrownBy(() -> {
            sut.bucketFill(faker.number().randomDigitNotZero(),
                    faker.number().randomDigitNotZero(),
                    faker.lorem().character());
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage("Canvas Not Found. Please create canvas first.");

    }

    @Test
    public void bucketFill_ColorNotBeCharX_Failure() {
        //Arrange
        var canvasWidth = 14;
        var canvasHeight = 5;
        var canvasToDraw = drawRectangle(getBlankCanvas(canvasWidth, canvasHeight), 3, 1, 7, 4);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);

        //Act , Assert
        assertThatThrownBy(() -> {
            sut.bucketFill(8, 3, 'x');
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input. Color must not be x");
    }

    @Test
    public void bucketFill_InvalidCoordinates_Failure() {
        //Arrange
        var canvasWidth = 14;
        var canvasHeight = 5;
        var canvasToDraw = getBlankCanvas(canvasWidth, canvasHeight);
        Mockito.when(mockInMemoryCanvas.getCanvas()).thenReturn(canvasToDraw);


        //Act, Assert
        assertThatThrownBy(() -> {
            sut.bucketFill(16, 0, 'o');
        }).isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid inputs. Coordinates are beyond canvas borders.");
    }


    /**
     * Provides invalid coordinates for drawing line or rectangle.
     *
     * @return test input arguments.
     */
    private static Stream<Arguments> provideInvalidCoordinates() {
        return Stream.of(
                Arguments.of("x1 must not be zero", 0, 2, 10, 3, "Invalid inputs. Coordinates are beyond canvas borders."),
                Arguments.of("x2 must not be zero", 1, 2, 0, 3, "Invalid inputs. Coordinates are beyond canvas borders."),
                Arguments.of("y coordinates must be in canvas", 1, 5, 10, 5, "Invalid inputs. Coordinates are beyond canvas borders."),
                Arguments.of("x1 must be less than x2", 12, 3, 10, 3, "Invalid inputs. x1 and y1 must be less than x2 and y2 respectively."),
                Arguments.of("y1 must be less than y2", 8, 3, 10, 2, "Invalid inputs. x1 and y1 must be less than x2 and y2 respectively.")
        );
    }

    /**
     * Helper method to provides a blank canvas as per given width and height.
     *
     * @param canvasWidth  Width of canvas.
     * @param canvasHeight Height of canvas.
     * @return A blank canvas as 2D character array.
     */
    private char[][] getBlankCanvas(int canvasWidth, int canvasHeight) {
        var canvas = new char[canvasHeight + 2][canvasWidth + 2];
        // Top Border of canvas.
        Arrays.fill(canvas[0], '-');
        // Bottom border of canvas.
        Arrays.fill(canvas[canvas.length - 1], '-');
        //Left border of canvas.
        for (int i = 1; i <= canvas.length - 2; i++) {
            canvas[i][0] = '|';
        }
        //Right border of canvas.
        for (int i = 1; i <= canvas.length - 2; i++) {
            canvas[i][canvas[i].length - 1] = '|';
        }

        return canvas;
    }

    /**
     * Helper method to draw either Horizontal or Vertical Line.
     *
     * @param canvas for drawing.
     * @param x1     Starts x coordinate of a line .
     * @param y1     Starts y coordinate of a line .
     * @param x2     Ends x coordindate of a line.
     * @param y2     Ends y coordindate of a line.
     * @return canvas with line draw in it.
     */

    private char[][] drawLine(char[][] canvas, int x1, int y1, int x2, int y2) {
        if (y1 == y2) {  //Horizontal Line
            for (int i = x1; i <= x2; i++) {
                canvas[y1][i] = 'x';
            }
        } else if (x1 == x2) { //Vertical Line
            for (int j = y1; j <= y2; j++) {
                canvas[j][x1] = 'x';
            }
        }

        return canvas;
    }

    /**
     * Helper method to draw a canvas with a rectangle inside it.
     *
     * @param canvas for drawing.
     * @param x1     Top left x coordinate of a rectangle.
     * @param y1     Top left y coordinate of a rectangle.
     * @param x2     Bottom right x coordinate of a rectangle.
     * @param y2     Bottom right y coordinate of a rectangle.
     * @return canvas with rectangle draw in it.
     */
    private char[][] drawRectangle(char[][] canvas, int x1, int y1, int x2, int y2) {

        //Upper line of rectangle.
        var updatedCanvas = drawLine(canvas, x1, y1, x2, y1);
        //Bottom line of rectangle.
        drawLine(updatedCanvas, x1, y2, x2, y2);
        //Left line of rectangle.
        drawLine(updatedCanvas, x1, y1, x1, y2);
        //Right line of rectangle.
        drawLine(updatedCanvas, x2, y1, x2, y2);

        return updatedCanvas;
    }
}
