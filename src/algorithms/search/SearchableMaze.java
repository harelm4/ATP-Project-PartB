package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 * Searchable algorithms.mazeGenerators.Maze class
 * Used as Object Adapter
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */
public class SearchableMaze implements ISearchable
{
    private Maze maze;

    /** Constructor */
    public SearchableMaze(Maze maze) { this.maze = maze; }

    @Override
    public AState getStartPosition() { return maze.getStartPosition() == null ? null : new MazeState(maze.getStartPosition(), 0, null); }

    @Override
    public AState getGoalPosition() { return maze.getGoalPosition() == null ? null : new MazeState(maze.getGoalPosition(), 0, null); }


    /**
     * This method return a list of legal states that can be reached from the current state.
     *
     * @param curState - the current state.
     * @return All possible neighbors states
     */
    @Override
    public ArrayList<AState> getAllSuccessors(AState curState)
    {
        if (!(curState instanceof MazeState)) return null;

        MazeState state = (MazeState) curState;
        ArrayList<AState> allPossibleState = new ArrayList<>();
        int row = state.getPosition().getRowIndex();
        int col = state.getPosition().getColumnIndex();

        boolean up = isMoveValid(row - 1, col);
        if (up) { allPossibleState.add(new MazeState(new Position(row - 1, col), curState.cost + 10, curState)); }

        boolean down = isMoveValid(row + 1, col);
        if (down) { allPossibleState.add(new MazeState(new Position(row + 1, col), curState.cost + 10, curState)); }

        boolean left = isMoveValid(row, col - 1);
        if (left) { allPossibleState.add(new MazeState(new Position(row, col - 1), curState.cost + 10, curState)); }

        boolean right = isMoveValid(row, col + 1);
        if (right) { allPossibleState.add(new MazeState(new Position(row, col + 1), curState.cost + 10, curState)); }

        boolean left_up = isMoveValid(row - 1, col - 1) && (left || up);
        if (left_up) { allPossibleState.add(new MazeState(new Position(row - 1, col - 1), curState.cost + 15, curState)); }

        boolean left_down = isMoveValid(row + 1, col - 1) && (left || down);
        if (left_down) { allPossibleState.add(new MazeState(new Position(row + 1, col - 1), curState.cost + 15, curState)); }

        boolean right_up = isMoveValid(row - 1, col + 1) && (right || up);
        if (right_up) { allPossibleState.add(new MazeState(new Position(row - 1, col + 1), curState.cost + 15, curState)); }

        boolean right_down = isMoveValid(row + 1, col + 1) && (right || down);
        if (right_down) { allPossibleState.add(new MazeState(new Position(row + 1, col + 1), curState.cost + 15, curState)); }

        return allPossibleState;
    }

    /**
     * This method checks if the move is valid.
     * @param row - row index
     * @param col - column index
     * @return true if the move is valid; otherwise false
     */
    private boolean isMoveValid(int row, int col)
    {
        try { return maze.getMaze()[row][col] == 0; }
        catch (Exception e) { return false; }
    }
}
