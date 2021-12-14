package com.drawingApp.drawing.app.commands;

import com.drawingApp.drawing.app.repositories.InMemoryCanvasRepository;

import java.util.Arrays;

/**
 * Provide a command object
 */
public class CommandFactory {

    private InMemoryCanvasRepository inMemoryCanvasRepository;

    /**
     * Creates new instance of {@link CommandFactory}
     *
     * @param inMemoryCanvasRepository See {@link InMemoryCanvasRepository}
     */
    public CommandFactory(InMemoryCanvasRepository inMemoryCanvasRepository) {
        this.inMemoryCanvasRepository = inMemoryCanvasRepository;
    }

    /**
     * Factory method to create instance of {@link Command} from input.
     *
     * @param inputCmd Input command.
     * @return Instance of {@link Command}. Can be null.
     */
    public Command getCommand(String inputCmd) {

        var commandTypeOptional = Arrays.stream(CommandType.values())
                .filter(cmd -> cmd.getPattern().matcher(inputCmd).matches())
                .findFirst();

        if (commandTypeOptional.isPresent()) {
            var commandType = commandTypeOptional.get();
            var params = inputCmd.split("\\s");
            switch (commandType) {
                case CANVAS:
                    return new Canvas(Integer.parseInt(params[1]),
                            Integer.parseInt(params[2]),
                            inMemoryCanvasRepository);
                case LINE:
                    return new Line(Integer.parseInt(params[1]),
                            Integer.parseInt(params[2]),
                            Integer.parseInt(params[3]),
                            Integer.parseInt(params[4]),
                            inMemoryCanvasRepository);
                case RECTANGLE:
                    return new Rectangle(Integer.parseInt(params[1]),
                            Integer.parseInt(params[2]),
                            Integer.parseInt(params[3]),
                            Integer.parseInt(params[4]),
                            inMemoryCanvasRepository);
                case BUCKET_FILL:
                    return new BucketFill(Integer.parseInt(params[1]),
                            Integer.parseInt(params[2]),
                            params[3].charAt(0),
                            inMemoryCanvasRepository);
                case QUIT:
                    return new Quit();
            }
        }
        return null;
    }

}
