package com.drawingApp.drawing.app.commands;

import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommandFactoryTests {

    Faker faker = new Faker();

    @Mock
    InMemoryCanvasRepository inMemoryCanvasRepository;

    @InjectMocks
    CommandFactory sut;

    @Test
    public void getCommand_Canvas_Success() {
        //Arrange
        var inputCmd = faker.regexify(CommandType.CANVAS.getRegEx());
        var params = inputCmd.split("\\s");
        var width = Integer.parseInt(params[1]);
        var height = Integer.parseInt(params[2]);
        var expectedCanvasCmd = new Canvas(width, height, inMemoryCanvasRepository);
        //Act
        var command = sut.getCommand(inputCmd);
        //Assert
        assertThat(command).isInstanceOf(Canvas.class);
        assertThat(command).usingRecursiveComparison().isEqualTo(expectedCanvasCmd);
    }

    @Test
    public void getCommand_Line_Success() {
        //Arrange
        var inputCmd = faker.regexify(CommandType.LINE.getRegEx());
        var params = inputCmd.split("\\s");
        var x1 = Integer.parseInt(params[1]);
        var y1 = Integer.parseInt(params[2]);
        var x2 = Integer.parseInt(params[3]);
        var y2 = Integer.parseInt(params[4]);
        var expectedLineCmd = new Line(x1, y1, x2, y2, inMemoryCanvasRepository);
        //Act
        var command = sut.getCommand(inputCmd);
        //Assert
        assertThat(command).isInstanceOf(Line.class);
        assertThat(command).usingRecursiveComparison().isEqualTo(expectedLineCmd);

    }

    @Test
    public void getCommand_Rectangle_Success() {
        //Arrange
        var inputCmd = faker.regexify(CommandType.RECTANGLE.getRegEx());
        var params = inputCmd.split("\\s");
        var x1 = Integer.parseInt(params[1]);
        var y1 = Integer.parseInt(params[2]);
        var x2 = Integer.parseInt(params[3]);
        var y2 = Integer.parseInt(params[4]);
        var expectedRectangleCmd = new Rectangle(x1, y1, x2, y2, inMemoryCanvasRepository);
        //Act
        var command = sut.getCommand(inputCmd);
        //Assert
        assertThat(command).isInstanceOf(Rectangle.class);
        assertThat(command).usingRecursiveComparison().isEqualTo(expectedRectangleCmd);

    }

    @Test
    public void getCommand_BucketFill_Success() {
        //Arrange
        var inputCmd = faker.regexify(CommandType.BUCKET_FILL.getRegEx());
        var params = inputCmd.split("\\s");
        var x = Integer.parseInt(params[1]);
        var y = Integer.parseInt(params[2]);
        var color = params[3].charAt(0);

        var expectedFillCmd = new BucketFill(x, y, color, inMemoryCanvasRepository);
        //Act
        var command = sut.getCommand(inputCmd);
        //Assert
        assertThat(command).isInstanceOf(BucketFill.class);
        assertThat(command).usingRecursiveComparison().isEqualTo(expectedFillCmd);

    }

    @Test
    public void getCommand_Quit_Success() {
        //Arrange
        var inputCmd = faker.regexify(CommandType.QUIT.getRegEx());
        //Act
        var command = sut.getCommand(inputCmd);
        //Assert
        assertThat(command).isInstanceOf(Quit.class);
    }

    @Test
    public void getCommand_InvalidInput_NoCommandProduced() {
        //Arrange
        var inputCmd = faker.lorem().word();
        //Act
        var command = sut.getCommand(inputCmd);
        //Assert
        assertThat(command).isNull();
    }
}
