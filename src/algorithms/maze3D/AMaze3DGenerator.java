package algorithms.maze3D;

/**
 * This class responsible to generate an instance of a 3D maze
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator
{
    /**
     * This method generate a new 3D maze by the depth, row and column parameters.
     *
     * @param depth - the number of depth in the maze
     * @param row - the number of rows in the maze
     * @param column - the number of columns in the maze
     * @return A 3D maze
     */
    @Override
    public abstract Maze3D generate(int depth, int row, int column);

    /**
     * This method measures the time it takes to generate a 3D maze.
     *
     * @param depth - the number of depth in the maze
     * @param row - the number of rows in the maze
     * @param column - the number of columns in the maze
     * @return A 3D maze generation time (ms).
     */
    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column)
    {
        if (depth < 2 || row < 2 || column < 2) { return -1; }

        long before = System.currentTimeMillis();
        generate(depth, row, column);
        long after = System.currentTimeMillis();
        long time = after - before;
        return time;
    }
}
