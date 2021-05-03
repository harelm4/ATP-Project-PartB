package algorithms.mazeGenerators;

import java.util.ArrayList;

/*******************************************************************************************
 *    ~-=-=-=-=~-=-~=-=-~=-=~-=-=~-=~ Prim's Algorithm  ~-=~=-~=~-=~-=~-=~-=-=-=-=-=-=~    *
 *******************************************************************************************
 * This class inherit the abstract class AMazeAlgorithm that implements the IMazeGenerator *
 * interface. This algorithm is a randomized version of Prim's algorithm.                  *
 *                                                                                         *
 * Pseudo code:                                                                            *
 * 1. Start with a grid full of walls.                                                     *
 * 2. Pick a cell, mark it as part of the maze.                                            *
 * 3. Add the walls of the cell to the wall list.                                          *
 * 4. While there are walls in the list:                                                   *
 *      4.1. Pick a random wall from the list.                                             *
 *      4.2. If only one of the two cells that the wall divides is visited, then:          *
 *          4.2.1. Make the wall a passage and mark the unvisited cell as part of the maze *
 *          4.2.2. Add the neighboring walls of the cell to the wall list.                 *
 *      4.3. Remove the wall from the list.                                                *
 *                                                                                         *
 * @author Eden_Hai                                                                        *
 * @version 2.0                                                                            *
 * @since 07-04-2021                                                                       *
 ******************************************************************************************/

public class MyMazeGenerator extends AMazeGenerator
{
    private ArrayList<Position> wallsOfTheCell;

    @Override
    public Maze generate(int rowSize, int colSize)
    {
        if (rowSize < 2 || colSize < 2) { return null; }

        Maze myMaze = new Maze(rowSize, colSize);
        myMaze.setRandomPosition();
        /* Start with a grid full of walls */
        myMaze.makeAllWalls();

        /* Create an empty ArrayList */
        wallsOfTheCell = new ArrayList<>();

        /* Pick a cell, mark it as the start position of the maze.
           Add the walls of the cell to the wall list. */
        wallsOfTheCell.add(myMaze.getStartPosition());

        Position current=null;
        Position goal=null;
        int count;

        /* While there are walls in the list: */
        while (!wallsOfTheCell.isEmpty())
        {
            /* Pick a random wall from the list. */
            current = wallsOfTheCell.remove((int) (Math.random() * wallsOfTheCell.size()));

            /* Check the number of visited neighbors */
            count = checkNumberOfVisitedNeighbors(myMaze.getMaze(), current);

            /* If only one of the two cells that the wall divides is visited: */
            if (count <= 1)
            {
                /* Make the wall a passage and mark the unvisited cell as part of the maze. */
                myMaze.breakWall(current.getRowIndex(), current.getColumnIndex());

                /* Add the neighboring walls of the cell to the wall list. */
                addValidAdjacentCells(myMaze.getMaze(), 0, current.getRowIndex(), current.getRowIndex() - 1, current.getColumnIndex());
                addValidAdjacentCells(myMaze.getMaze(), 0, current.getColumnIndex(), current.getRowIndex(), current.getColumnIndex() - 1);
                addValidAdjacentCells(myMaze.getMaze(), current.getRowIndex() + 1, myMaze.getRowSize(), current.getRowIndex() + 1, current.getColumnIndex());
                addValidAdjacentCells(myMaze.getMaze(), current.getColumnIndex() + 1, myMaze.getColSize(), current.getRowIndex(), current.getColumnIndex() + 1);
                goal=current;
            }
        }
        myMaze.setGoalPosition(goal);
        return myMaze;
    }

    /**
     * Check for each adjacent cell of current position if its already visited
     *
     * @return the number of neighbors that has the value of 0 and in the valid range of the maze
     */
    private int checkNumberOfVisitedNeighbors(int[][] maze, Position current)
    {
        int numOfValidNeighbors = 0;
        if (current.getRowIndex() - 1 >= 0 && maze[current.getRowIndex() - 1][current.getColumnIndex()] == 0) { numOfValidNeighbors++; }
        if (current.getRowIndex() + 1 < maze.length && maze[current.getRowIndex() + 1][current.getColumnIndex()] == 0) { numOfValidNeighbors++; }
        if (current.getColumnIndex() - 1 >= 0 && maze[current.getRowIndex()][current.getColumnIndex() - 1] == 0) { numOfValidNeighbors++; }
        if (current.getColumnIndex() + 1 < maze[0].length && maze[current.getRowIndex()][current.getColumnIndex() + 1] == 0) { numOfValidNeighbors++; }
        return numOfValidNeighbors;
    }

    /**
     * Add the neighboring walls of the cell to the wall list.
     */
    private void addValidAdjacentCells(int[][] maze, int pos, int limit, int row, int col)
    {
        if (pos < limit && maze[row][col] == 1)
        {
            Position position = new Position(row, col);
            wallsOfTheCell.add(position);
        }
    }

}
