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
public class CanvasTests {

    Faker faker = new Faker();

    @Mock
    InMemoryCanvasRepository inMemoryCanvasRepository;

    @Test
    public void execute_success() throws InvalidInputException {
        //Arrange
        var width = faker.number().randomDigitNotZero();
        var height = faker.number().randomDigitNotZero();
        Canvas canvas = new Canvas(width, height, inMemoryCanvasRepository);
        //Act
        canvas.execute();
        //Assert
        Mockito.verify(inMemoryCanvasRepository).newCanvas(width, height);
    }

    @Test
    public void execute_failure() throws InvalidInputException {
        //Arrange
        var width = faker.number().randomDigitNotZero();
        var height = faker.number().randomDigitNotZero();
        Canvas canvas = new Canvas(width, height, inMemoryCanvasRepository);

        Mockito.doThrow(InvalidInputException.class).when(inMemoryCanvasRepository).newCanvas(width, height);

        //Act, Assert
        assertThatThrownBy(canvas::execute).isInstanceOf(InvalidInputException.class);

    }
}
