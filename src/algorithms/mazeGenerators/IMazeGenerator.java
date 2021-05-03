package algorithms.mazeGenerators;

/**
 * An interface class of generating a 2D maze
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 31-03-2021
 */
public interface IMazeGenerator
{
    /**
     * This method generate a new 2D maze by the row and column parameters.
     * @param rowSize - the number of rows in the maze
     * @param colSize - the number of columns in the maze
     * @return A 2D maze
     */
    Maze generate(int rowSize, int colSize);

    /**
     * This method measures the time it takes to generate a 2D maze.
     * @param rowSize - the number of rows in the maze
     * @param colSize - the number of columns in the maze
     * @return maze generation time (ms).
     */
    long measureAlgorithmTimeMillis(int rowSize, int colSize);
}
