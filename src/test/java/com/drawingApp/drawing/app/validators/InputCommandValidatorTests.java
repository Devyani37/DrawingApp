package com.drawingApp.drawing.app.validators;

import com.drawingApp.drawing.app.commands.CommandType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class InputCommandValidatorTests {

    static Faker faker = new Faker();

    InputCommandValidator sut = new InputCommandValidator();

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("provideValidUserInputs")
    public void validInput_Success(String inputCommand) {
        assertThat(sut.isValid(inputCommand)).isTrue();
    }

    @Test
    public void invalidInput_Failure() {
        String inputCommand = faker.lorem().word();
        assertThat(sut.isValid(inputCommand)).isFalse();
    }

    /**
     * Provide valid user's inputs.
     * @return test input arguments.
     */
    private static Stream<Arguments> provideValidUserInputs() {
        return Stream.of(
                Arguments.of(faker.regexify(CommandType.CANVAS.getRegEx())),
                Arguments.of(faker.regexify(CommandType.LINE.getRegEx())),
                Arguments.of(faker.regexify(CommandType.RECTANGLE.getRegEx())),
                Arguments.of(faker.regexify(CommandType.BUCKET_FILL.getRegEx())),
                Arguments.of(faker.regexify(CommandType.QUIT.getRegEx()))
        );
    }
}
