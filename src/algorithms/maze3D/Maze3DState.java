package algorithms.maze3D;

import algorithms.search.AState;

public class Maze3DState extends AState
{
    Position3D curPosition;

    /**
     * Constructor
     *
     * @param curPosition - current position
     * @param cost - cost to reach this state
     * @param parent - patent state
     */
    public Maze3DState(Position3D curPosition, int cost, AState parent)
    {
        super(cost, parent);
        this.curPosition = curPosition;
    }

    public Position3D getPosition() { return curPosition; }

    @Override
    public String toString() { return curPosition.toString(); }

    /**
     * Compare between two mazeStates based on the position
     *
     * @param obj - the other mazeState to check equality with
     * @return true if equals; otherwise false
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        Maze3DState other = (Maze3DState) obj;
        return other.getPosition().equals(curPosition);
    }
}
