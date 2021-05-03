package algorithms.search;

import java.io.Serializable;

/**
 * An abstract class representing a state of a Position (his value, parentCell, Position)
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */
public abstract class AState implements Serializable
{
    public int cost;
    public AState cameFrom;

    public AState(int cost, AState cameFrom)
    {
        this.cost = cost;
        this.cameFrom = cameFrom;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public AState getCameFrom()
    {
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom)
    {
        this.cameFrom = cameFrom;
    }

    public abstract boolean equals(Object o);
}
