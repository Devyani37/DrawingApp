package com.drawingApp.drawing.app.repositories;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryCanvasTests {

    InMemoryCanvas sut = new InMemoryCanvas();
    Faker fake = new Faker();

    @Test
    public void saveCanvas_Success() {
        //Arrange
        var canvasWidth = fake.number().randomDigitNotZero();
        var canvasHeight = fake.number().randomDigitNotZero();
        var newCanvas = new char[canvasHeight][canvasWidth];
        //Act
        var savedCanvas = sut.save(newCanvas);
        //Assert
        assertThat(savedCanvas).isDeepEqualTo(newCanvas);
    }

    @Test
    public void getCanvas_ReturnEmptyCanvas() {
        var canvas = sut.getCanvas();
        assertThat(canvas.length).isEqualTo(0);
    }

}
