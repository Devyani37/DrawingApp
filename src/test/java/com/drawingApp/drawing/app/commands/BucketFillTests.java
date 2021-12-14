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
public class BucketFillTests {

    Faker faker = new Faker();

    @Mock
    InMemoryCanvasRepository inMemoryCanvasRepository;

    @Test
    public void execute_success() throws InvalidInputException {
        //Arrange
        var x = faker.number().randomDigitNotZero();
        var y = faker.number().randomDigitNotZero();
        char color = faker.lorem().character();

        BucketFill bucketFill = new BucketFill(x, y, color, inMemoryCanvasRepository);
        //Act
        bucketFill.execute();
        //Assert
        Mockito.verify(inMemoryCanvasRepository).bucketFill(x, y, color);
    }

    @Test
    public void execute_failure() throws InvalidInputException {
        //Arrange
        var x = faker.number().randomDigitNotZero();
        var y = faker.number().randomDigitNotZero();
        char color = faker.lorem().character();
        BucketFill bucketFill = new BucketFill(x, y, color, inMemoryCanvasRepository);

        Mockito.doThrow(InvalidInputException.class).when(inMemoryCanvasRepository).bucketFill(x,y,color);

        //Act, Assert
        assertThatThrownBy(bucketFill::execute).isInstanceOf(InvalidInputException.class);

    }
}
