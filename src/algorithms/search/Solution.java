package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines result of a algorithms.search in a way
 * that we can save and go over the states that bring us to the goal state.
 * A legal sequence of states from start state to goal state.
 *
 * @author Eden_Hai
 * @version 2.0
 * @since 08-04-2021
 */
public class Solution implements Serializable
{
    private ArrayList<AState> solutionPath;

    public Solution(ArrayList<AState> solutionPath)
    {
        this.solutionPath = solutionPath;
    }

    public ArrayList<AState> getSolutionPath() {return this.solutionPath == null ? new ArrayList<>() : this.solutionPath;}
}
