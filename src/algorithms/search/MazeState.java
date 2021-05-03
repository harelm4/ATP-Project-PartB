package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState implements Serializable
{
    Position curPosition;

    /**
     * Constructor
     *
     * @param curPosition - current position
     * @param cost - cost to reach this state
     * @param parent - patent state
     */
    public MazeState(Position curPosition, int cost, AState parent)
    {
        super(cost, parent);
        this.curPosition = curPosition;
    }

    public Position getPosition() { return curPosition; }

    public void setPosition(Position curPosition) { this.curPosition = curPosition;}

    @Override
    public String toString() { return curPosition.toString(); }

    /**
     * Compare between two mazeStates based on the position
     * @param obj - the other mazeState to check equality with
     * @return true if equals; otherwise false
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        MazeState other = (MazeState) obj;
        return other.getPosition().equals(curPosition);
    }
}
