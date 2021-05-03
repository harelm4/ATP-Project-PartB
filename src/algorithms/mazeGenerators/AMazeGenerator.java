package algorithms.mazeGenerators;

/**
 * This class responsible to generate an instance of a 2D maze
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */
public abstract class AMazeGenerator implements IMazeGenerator
{
    /**
     * This method generate a new 2D maze by the row and column parameters.
     * @param rowSize - the number of rows in the maze
     * @param colSize - the number of columns in the maze
     * @return A 2D maze
     */
    @Override
    public abstract Maze generate(int rowSize, int colSize);

    /**
     * This method measures the time it takes to generate a 2D maze.
     * @param rowSize - the number of rows in the maze
     * @param colSize - the number of columns in the maze
     * @return maze generation time (ms).
     */
    @Override
    public long measureAlgorithmTimeMillis(int rowSize, int colSize)
    {
        if (rowSize < 2 || colSize < 2) { return -1; }

        long before = System.currentTimeMillis();
        generate(rowSize, colSize);
        long after = System.currentTimeMillis();
        long time = after - before;
        return time;
    }
}
