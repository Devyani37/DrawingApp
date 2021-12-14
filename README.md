# Drawing App

This is a simple console drawing program in Java.

## Requirement

- Java 11
- Apache Maven 3

## How it Works

The program works as follows:

1. Create a new canvas.

2. Draw on the canvas by issuing various commands.

3. Quit.

````bash
Command	         Description

C w h	        Create a new canvas of width w and height h.
L x1 y1 x2 y2	Draw a new line from (x1,y1) to (x2,y2). Currently, only
                horizontal or vertical lines are supported. Horizontal and vertical lines
                will be drawn using the 'x' character.
R x1 y1 x2 y2	Draw a rectangle whose upper left corner is (x1,y1) and
                lower right corner is (x2,y2). Horizontal and vertical lines will be drawn
                using the 'x' character.
B x y c	        Fill the entire area connected to (x,y) with "colour" c. The
                behaviour of this is the same as that of the "bucket fill" tool in paint
                programs.
Q               Quit the program.


````
## Sample I/O
Below is a sample run of the program. User input is prefixed with enter command:

````
enter command: C 20 4
----------------------
|                    |
|                    |
|                    |
|                    |
----------------------
enter command: L 1 2 6 2
----------------------
|                    |
|xxxxxx              |
|                    |
|                    |
----------------------

enter command: L 6 3 6 4
----------------------
|                    |
|xxxxxx              |
|     x              |
|     x              |
----------------------

enter command: R 14 1 18 3
----------------------
|             xxxxx  |
|xxxxxx       x   x  |
|     x       xxxxx  |
|     x              |
----------------------

enter command: B 10 3 o
----------------------
|oooooooooooooxxxxxoo|
|xxxxxxooooooox   xoo|
|     xoooooooxxxxxoo|
|     xoooooooooooooo|
----------------------

enter command: Q
````
## How to run

- Create executable jar file `mvn clean package`
- This will generate an executable jar on location `target/drawing-app-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
- Execute the jar `java -jar target/drawing-app-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
- Enter commands as mentioned above.

## Execute tests

- To execute tests run `mvn clean test`
- This will execute various tests and display the results.
