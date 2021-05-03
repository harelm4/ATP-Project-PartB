package algorithms.search;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm
{
    public BreadthFirstSearch()
    {
        this.numberOfNodesEvaluated = 0;
        this.visitedCells = new HashMap<>();
        this.queue = new LinkedTransferQueue<>();
    }
    /**
     * @param domain
     * @return BFS implementation when the vertexes are the cells and there are edges between every cell to another if there is a path
     *         between them
     */
    @Override
    public Solution solve(ISearchable domain)
    {
        if (domain == null) return null;
        // 2      let Q be a queue
        // 3      label root as discovered
        // 4      Q.enqueue(root)
        visitedCells.put((domain.getStartPosition()).toString(), domain.getStartPosition());
        queue.add(domain.getStartPosition());
        AState cur = null;
        while (!queue.isEmpty())
        {
            //v := Q.dequeue()
            cur = queue.remove();

            // 7          if v is the goal then
            // 8              return v
            if (cur.equals(domain.getGoalPosition()))
                break;

            ArrayList<AState> neighbors = domain.getAllSuccessors(cur);

            // 9          for all edges from v to w in G.adjacentEdges(v) do
            //10              if w is not labeled as discovered then
            //11                  label w as discovered
            //12                  Q.enqueue(w)
            for (AState state : neighbors)
            {
                if (!visitedCells.containsKey(state.toString()))
                {
                    visitedCells.put(state.toString(), state);
                    queue.add(state);
                    state.setCameFrom(cur);
                    numberOfNodesEvaluated++;
                }
            }
        }
        return backtracePath(cur);
    }

    @Override
    public String getName() {return "BreadthFirstSearch";}
}
