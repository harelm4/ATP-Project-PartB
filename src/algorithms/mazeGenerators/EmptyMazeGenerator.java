package algorithms.mazeGenerators;

/**
 * A class that generates an empty maze
 *
 * @author Eden_Hai
 * @version 2.0
 * @since 07-04-2021
 */
public class EmptyMazeGenerator extends AMazeGenerator
{
    @Override
    public Maze generate(int rowSize, int colSize)
    {
        if (rowSize < 2 || colSize < 2) { return null; }
        Maze emptyMaze = new Maze(rowSize, colSize);
        emptyMaze.setRandomPosition();
        Position start = emptyMaze.getStartPosition();
        Position goal = emptyMaze.getGoalPosition();
        if (emptyMaze.isPositionOnEdges(start) || emptyMaze.isPositionOnEdges(goal)) { return null; }
        return emptyMaze;
    }
}
