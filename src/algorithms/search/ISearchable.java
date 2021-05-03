package algorithms.search;

import java.util.ArrayList;

/**
 * An interface of algorithms.search structs that provides a common functions.
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */
public interface ISearchable
{
    /**
     * This method return the initial situation from which we want to begin to search.
     *
     * @return algorithms.search.AState
     */
    AState getStartPosition();


    /**
     * This method return the goal situation we want to reach.
     *
     * @return algorithms.search.AState
     */
    AState getGoalPosition();


    /**
     * This method return a list of legal states that can be reached from the current state.
     * @param state - the current state.
     * @return All possible neighbors states
     */
    ArrayList<AState> getAllSuccessors(AState state);
}
