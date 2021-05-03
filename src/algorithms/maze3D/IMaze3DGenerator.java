package algorithms.maze3D;

/**
 * An interface class of generating a 3D maze
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 31-03-2021
 */
public interface IMaze3DGenerator
{
    /**
     * This method generate a new 3D maze by the depth, row and column parameters.
     *
     * @param depth - the number of depth in the maze
     * @param row - the number of rows in the maze
     * @param column - the number of columns in the maze
     * @return A 3D maze
     */
    Maze3D generate(int depth, int row, int column);

    /**
     * This method measures the time it takes to generate a 3D maze.
     *
     * @param depth - the number of depth in the maze
     * @param row - the number of rows in the maze
     * @param column - the number of columns in the maze
     * @return A 3D maze generation time (ms).
     */
    long measureAlgorithmTimeMillis(int depth, int row, int column);
}
