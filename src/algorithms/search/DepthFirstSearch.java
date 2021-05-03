package algorithms.search;

import java.util.*;

/**
 * This class uses the 'DFS algorithm' to solve the problem.
 * It Implements the algorithm data structure as a Stack.
 *
 * @author Eden_Hai
 * @version 2.0
 * @since 08-04-2021
 */
public class DepthFirstSearch extends ASearchingAlgorithm
{
    private Stack<AState> stack;

    public DepthFirstSearch()
    {
        this.numberOfNodesEvaluated = 0;
        this.stack = new Stack<>();
        this.visitedCells = new HashMap<>();
    }

    @Override
    public Solution solve(ISearchable domain)
    {
        if (domain == null) return null;

        /* Add the start position to stack. */
        stack.push(domain.getStartPosition());

        /* Mark the start position as visited. */
        visitedCells.put(domain.getStartPosition().toString(), domain.getStartPosition());

        AState curState = null;

        while (!stack.isEmpty())
        {
            /* Pop the current state from top of the stack. */
            curState = stack.pop();

            /* If curState is the goal position return the solution path. */
            if (curState.equals(domain.getGoalPosition())) break;

            ArrayList<AState> neighbors = domain.getAllSuccessors(curState);

            for (AState state : neighbors)
            {
                if (!visitedCells.containsKey(state.toString()))
                {
                    stack.push(state);
                    state.setCameFrom(curState);
                    visitedCells.put(state.toString(), state);
                    numberOfNodesEvaluated++;
                }
            }
        }
        return backtracePath(curState);
    }

    @Override
    public String getName() {return "DepthFirstSearch";}
}

