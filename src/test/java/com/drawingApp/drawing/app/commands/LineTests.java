package com.drawingApp.drawing.app.commands;

import com.drawingApp.drawing.app.exceptions.InvalidInputException;
import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class LineTests {
    Faker faker = new Faker();

    @Mock
    InMemoryCanvasRepository inMemoryCanvasRepository;

    @Test
    public void execute_success() throws InvalidInputException {
        //Arrange
        var x1 = faker.number().randomDigitNotZero();
        var y1 = faker.number().randomDigitNotZero();
        var x2 = faker.number().randomDigitNotZero();
        var y2 = faker.number().randomDigitNotZero();

        Line newLine = new Line(x1,y1,x2,y2, inMemoryCanvasRepository);

        //Act
        newLine.execute();
        //Assert
        Mockito.verify(inMemoryCanvasRepository).drawLine(x1,y1,x2,y2);
    }

    @Test
    public void execute_failure() throws InvalidInputException {
        //Arrange
        var x1 = faker.number().randomDigitNotZero();
        var y1 = faker.number().randomDigitNotZero();
        var x2 = faker.number().randomDigitNotZero();
        var y2 = faker.number().randomDigitNotZero();
        Line line = new Line(x1,y1,x2,y2, inMemoryCanvasRepository);

        Mockito.doThrow(InvalidInputException.class).when(inMemoryCanvasRepository).drawLine(x1,y1,x2,y2);

        //Act, Assert
        assertThatThrownBy(line::execute).isInstanceOf(InvalidInputException.class);

    }
}
