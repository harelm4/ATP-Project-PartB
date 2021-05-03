package algorithms.search;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract class of algorithms.search algorithms that containing the data structures needed for the Algorithms.
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    protected int numberOfNodesEvaluated;
    protected HashMap<String, AState> visitedCells;
    protected AbstractQueue<AState> queue;

    @Override
    public abstract Solution solve(ISearchable domain);

    @Override
    public int getNumberOfNodesEvaluated() {return this.numberOfNodesEvaluated;}

    /**
     * This method returns a solution path by backtracking from goal state to start state
     * @param goalState - Position that begin walking back from
     * @return The solution
     */
    public Solution backtracePath(AState goalState)
    {
        ArrayList<AState> solutionPath = new ArrayList<>();

        solutionPath.add(0, goalState);

        AState state = goalState.getCameFrom();

        while (state != null)
        {
            solutionPath.add(0, state);
            state = state.getCameFrom();
        }
        return new Solution(solutionPath);
    }
}
