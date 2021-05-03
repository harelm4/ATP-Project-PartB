package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A class representing a location in the 2D maze
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 29-03-2021
 */

public class Position implements Serializable
{
    private int rowIndex;
    private int colIndex;

    /**
     * Constructor
     *
     * @param row row index
     * @param column column index
     */
    public Position(int row, int column)
    {
        this.rowIndex = row;
        this.colIndex = column;
    }

    public int getColumnIndex() { return colIndex; }

    public int getRowIndex() { return rowIndex; }

    /**
     * Indicates whether some other position is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        Position other = (Position) obj;
        return this.rowIndex == other.rowIndex && this.colIndex == other.colIndex;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit
     * of hash tables such as those provided by {@link HashMap}.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() { return super.hashCode(); }

    /**
     * Override toString() to print position as format {row,column}
     * @return a string representation of the position.
     */
    @Override
    public String toString() { return "{" + rowIndex + "," + colIndex + "}"; }
}
