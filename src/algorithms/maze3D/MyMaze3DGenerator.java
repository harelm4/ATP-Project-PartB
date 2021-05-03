package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator
{
    private ArrayList<Position3D> walls;

    @Override
    public Maze3D generate(int depth, int row, int column)
    {
        if (depth < 2 || row < 2 || column < 2) return null;

        int[][][] maze = new int[depth][row][column];

        // Start with a grid full of walls
        makeAllWalls(maze, depth, row, column);

        Position3D start = setStartPosition(maze, depth, row, column);
        walls = new ArrayList<>();
        walls.add(start);
        Position3D current;
        int count;
        int out = 0;
        int in = 0;
        while (!walls.isEmpty())
        {
            current = walls.remove((int) (Math.random() * walls.size()));
            //count how many valid,0 valued neighbors current has
            count = checkNumberOfVisitedNeighbors(maze, current);

            if (count <= 1)
            {
                in++;
                //break random wall
                maze[current.getDepthIndex()][current.getRowIndex()][current.getColumnIndex()] = 0;
                //add new walls to "walls" list if they are valid walls and actually walls:
                //up
                addValidAdjacentCells(maze, 0, current.getRowIndex(), current.getRowIndex() - 1, current.getColumnIndex(), current.getDepthIndex());
                //left
                addValidAdjacentCells(maze, 0, current.getColumnIndex(), current.getRowIndex(), current.getColumnIndex() - 1, current.getDepthIndex());
                //down
                addValidAdjacentCells(maze, current.getRowIndex() + 1, maze[0].length, current.getRowIndex() + 1, current.getColumnIndex(), current.getDepthIndex());
                //right
                addValidAdjacentCells(maze, current.getColumnIndex() + 1, maze[0][0].length, current.getRowIndex(), current.getColumnIndex() + 1, current.getDepthIndex());
                //out
                addValidAdjacentCells(maze, 0, current.getDepthIndex(), current.getRowIndex(), current.getColumnIndex(), current.getDepthIndex() - 1);
                //in
                addValidAdjacentCells(maze, current.getDepthIndex() + 1, maze.length, current.getRowIndex(), current.getColumnIndex(), current.getDepthIndex() + 1);

            }
            else
            {
                out++;
            }
        }

        Position3D goal = setGoalPosition(maze, start);
        Maze3D myMaze = new Maze3D(maze, start, goal);
        return myMaze;
    }

    private void makeAllWalls(int[][][] maze, int depth, int row, int column)
    {
        for (int k = 0; k < depth; k++)
        {
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                    maze[k][i][j] = 1;
            }
        }
    }

    /**
     * set a random maze starting point
     */
    private Position3D setStartPosition(int[][][] maze, int depth, int row, int column)
    {
        Random random = new Random();
        int rowIndex = random.nextInt(row);
        int colIndex;
        int depIndex = random.nextInt(depth);

        if (rowIndex == 0 || rowIndex == row - 1 || depIndex == 0 || depIndex == depth - 1) { colIndex = random.nextInt(column); }

        else { colIndex = ((int) Math.round(Math.random())) * (column - 1); }

        maze[depIndex][rowIndex][colIndex] = 0;

        Position3D start = new Position3D(depIndex,rowIndex, colIndex);
        return start;
    }

    private Position3D setGoalPosition(int[][][] maze, Position3D start)
    {
        Random r = new Random();
        int depIndex = start.getDepthIndex();
        int rowIndex = start.getRowIndex();
        int colIndex = start.getColumnIndex();

        while ((start.getRowIndex() == rowIndex && start.getColumnIndex() == colIndex && start.getDepthIndex() == depIndex) || maze[depIndex][rowIndex][colIndex] == 1)
        {
            //pick a random row
            rowIndex = r.nextInt(maze[0].length);
            //pick a random depth
            depIndex = r.nextInt(maze.length);
            //if the row is first or last-pick a random cell
            //else-pick col 0 or end col randomly
            if (rowIndex == 0 || rowIndex == maze[0].length - 1 || depIndex == maze.length - 1 || depIndex == 0)
            {
                colIndex = r.nextInt(maze[0][0].length);
            }
            else
            {
                colIndex = ((int) (Math.round(Math.random())) * (maze[0][0].length - 1));
            }

        }
        maze[depIndex][rowIndex][colIndex] = 0;
        Position3D goal = new Position3D(depIndex,rowIndex, colIndex);
        return goal;
    }

    /**
     * returns the number of neighbors that has the value of 0 and in the valid range of the maze
     */
    private int checkNumberOfVisitedNeighbors(int[][][] maze, Position3D current)
    {
        int numOfValidNeighbors = 0;
        //up
        if (current.getRowIndex() - 1 > 0 && maze[current.getDepthIndex()][current.getRowIndex() - 1][current.getColumnIndex()] == 0) numOfValidNeighbors++;
        //down
        if (current.getRowIndex() + 1 < maze[0].length && maze[current.getDepthIndex()][current.getRowIndex() + 1][current.getColumnIndex()] == 0) numOfValidNeighbors++;
        //left
        if (current.getColumnIndex() - 1 > 0 && maze[current.getDepthIndex()][current.getRowIndex()][current.getColumnIndex() - 1] == 0) numOfValidNeighbors++;
        //right
        if (current.getColumnIndex() + 1 < maze[0][0].length && maze[current.getDepthIndex()][current.getRowIndex()][current.getColumnIndex() + 1] == 0) numOfValidNeighbors++;
        ///in
        if (current.getDepthIndex() + 1 < maze.length && maze[current.getDepthIndex() + 1][current.getRowIndex()][current.getColumnIndex()] == 0) numOfValidNeighbors++;
        //out
        if (current.getDepthIndex() - 1 > 0 && maze[current.getDepthIndex() - 1][current.getRowIndex()][current.getColumnIndex()] == 0) numOfValidNeighbors++;

        return numOfValidNeighbors;
    }

    private void addValidAdjacentCells(int[][][] maze, int bottom, int upper, int row, int col, int dep)
    {
        if (bottom < upper && maze[dep][row][col] == 1)
        {
            Position3D position = new Position3D(dep, row, col);
            walls.add(position);
        }
    }
}
