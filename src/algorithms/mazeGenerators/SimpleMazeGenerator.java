package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that generates a simple maze
 *
 * @author Eden_Hai
 * @version 2.0
 * @since 07-04-2021
 */
public class SimpleMazeGenerator extends AMazeGenerator
{
    private ArrayList<Position> visitedCells;

    @Override
    public Maze generate(int rowSize, int colSize)
    {
        if (rowSize < 2 || colSize < 2) { return null; }

        /* Create a maze with no walls */
        Maze simpleMaze = new Maze(rowSize, colSize);

        Random random = new Random();
        Position start = simpleMaze.getStartPosition();
        Position goal = simpleMaze.getGoalPosition();

        if (simpleMaze.isPositionOnEdges(start) || simpleMaze.isPositionOnEdges(goal)) { return null; }

        /* Start with a grid full of walls */
        simpleMaze.makeAllWalls();
        simpleMaze.breakWall(start.getRowIndex(), start.getColumnIndex());


        visitedCells = new ArrayList<>(); // list of visited cells

        visitedCells.add(start);

        ArrayList<Position> neighborsList = getRightDownNeighbors(simpleMaze, start); // list of neighbors to choose from
        Position neighbor;
        int i;
        while (!visitedCells.contains(goal))
        {
            i = random.nextInt(neighborsList.size());
            neighbor = neighborsList.get(i);
            visitedCells.add(neighbor);
            simpleMaze.breakWall(neighbor.getRowIndex(), neighbor.getColumnIndex());
            neighborsList = getRightDownNeighbors(simpleMaze, neighbor);
        }
        return randomZeros(goal, simpleMaze);
    }

    /**
     * rightDownNeighbor - a neighbor of p with has the value of 1 and located down or right in relation to p
     *
     * @param p - the position that checked
     * @return A list of the right or down neighbors of p
     */
    protected ArrayList<Position> getRightDownNeighbors (Maze maze, Position p)
    {
        ArrayList<Position> l = new ArrayList<>();

        if ((p.getRowIndex() < maze.getRowSize() - 1) && (maze.getMaze()[p.getRowIndex() + 1][p.getColumnIndex()] == 1)) { l.add(new Position(p.getRowIndex() + 1, p.getColumnIndex())); }

        if (p.getColumnIndex() < maze.getColSize() - 1 && maze.getMaze()[p.getRowIndex()][p.getColumnIndex() + 1] == 1) { l.add(new Position(p.getRowIndex(), p.getColumnIndex() + 1)); }

        return l;
    }


    private Maze randomZeros(Position goal, Maze m)
    {
        Random r = new Random();
        int row, col;
        for (int i = 0; i < goal.getRowIndex() * goal.getColumnIndex() ; i++)
        {
            row = r.nextInt(goal.getRowIndex()+1);
            col = r.nextInt(goal.getColumnIndex()+1);
            Position position = new Position(row, col);
            if (!visitedCells.contains(position))
                m.breakWall(row, col);
        }
        return m;
    }
}
