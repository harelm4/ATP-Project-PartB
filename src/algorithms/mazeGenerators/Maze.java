package algorithms.mazeGenerators;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 * The maze is represented as a 2D char array.
 * Zero ('0') - Pass
 * One ('1') - Wall
 * Start ('S') - The start position
 * End ('E') - The goal position
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 31-03-2021
 */
public class Maze implements Serializable {
    public int[][] m_maze;
    private int m_rowSize;
    private int m_colSize;
    Position m_start;
    Position m_goal;
    static int serialNum = 0;

    public Maze(int rowSize, int colSize) {
        m_rowSize = rowSize;
        m_colSize = colSize;
        m_maze = new int[rowSize][colSize];
        m_start = new Position(0, 0);
        m_goal = new Position(rowSize - 1, colSize - 1);
        serialNum++;
    }

    public int getRowSize() {
        return m_rowSize;
    }

    public int getColSize() {
        return m_colSize;
    }

    public Position getStartPosition() {
        return m_start;
    }

    public Position getGoalPosition() {
        return m_goal;
    }

    public int[][] getMaze() {
        return m_maze;
    }

    public void setStartPosition(Position startPosition) {
        this.m_start = startPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.m_goal = goalPosition;
    }

    public void setMaze(int[][] maze) {
        this.m_maze = maze;
    }

    public void setRandomPosition() {
        Random random = new Random();
        int rowIndex = random.nextInt(m_rowSize);
        int colIndex;

        /* ----------- UP ----------- */
        if (rowIndex == 0) {
            colIndex = random.nextInt(m_colSize);
        }

        /* ---------- DOWN ---------- */
        else if (rowIndex == m_rowSize - 1) {
            colIndex = random.nextInt(m_colSize);
        } else {
            int r = random.nextInt(2);

            /* ---------- LEFT ---------- */
            if (r == 0) {
                colIndex = 0;
            }

            /* ---------- RIGHT ---------- */
            else {
                colIndex = m_colSize - 1;
            }
        }
        m_start = new Position(rowIndex, colIndex);

        while (m_start.getRowIndex() == rowIndex || m_start.getColumnIndex() == colIndex || m_maze[rowIndex][colIndex] == 1) {
            /* pick a random row */
            rowIndex = random.nextInt(m_rowSize);

            /* if the row is first or last - pick a random cell */
            if (rowIndex == 0 || rowIndex == m_rowSize - 1) {
                colIndex = random.nextInt(m_colSize);
            }

            /* else - pick col 0 or end col randomly */
            else {
                colIndex = ((int) (Math.round(Math.random())) * (m_colSize - 1));
            }
        }
        m_goal = new Position(rowIndex, colIndex);
    }

    protected void addWall(int row, int col) {
        m_maze[row][col] = 1;
    }

    protected void breakWall(int row, int col) {
        m_maze[row][col] = 0;
    }

    /**
     * make all cells in the maze '1'
     */
    protected void makeAllWalls() {
        for (int i = 0; i < m_rowSize; i++) {
            for (int j = 0; j < m_colSize; j++)
                this.addWall(i, j);
        }
    }

    /**
     * This method print the maze where:
     * pass mark with "0"
     * wall mark with "1"
     * start mark with "S"
     * goal mark with "E"
     */
    public void print() {
        for (int row = 0; row < m_rowSize; row++) {
            System.out.print("{ ");
            for (int col = 0; col < m_colSize; col++) {
                /* if the position is the start - mark with S */
                if (row == m_start.getRowIndex() && col == m_start.getColumnIndex()) {
                    System.out.print("S ");
                } else {
                    /* if the position is the goal - mark with E */
                    if (row == m_goal.getRowIndex() && col == m_goal.getColumnIndex()) {
                        System.out.print("E ");
                    } else {
                        System.out.print(m_maze[row][col] + " ");
                    }
                }
            }
            System.out.println("}");
        }
    }

    /**
     * This method checks if a position is on the edge of the maze
     *
     * @param p - the position that wants to check
     * @return true if p on some edge; otherwise false
     */
    protected boolean isPositionOnEdges(Position p) {
        return p.getColumnIndex() != m_colSize - 1 && p.getColumnIndex() != 0 && p.getRowIndex() != m_rowSize - 1 && p.getRowIndex() != 0;
    }

//-------------------------------Part B--------------------------------------------------

    /**
     * returns a byte array representation
     * when -1 is an indicator for the end of the row
     * start is 2
     * goal is 3
     */
    public byte[] toByteArray() {
        byte[] b = new byte[this.getRowSize() * this.getColSize() + this.getRowSize()];
        int index = 0;
        Integer integerObject;
        for (int i = 0; i < this.getRowSize(); i++) {
            for (int j = 0; j < this.getColSize(); j++) {
                //converting the start position
                if (i == this.m_start.getRowIndex() && j == this.m_start.getColumnIndex()) {
                    integerObject = 2;
                    b[index] = integerObject.byteValue();
                    index++;
                    continue;
                }
                //converting the end position
                if (i == this.m_goal.getRowIndex() && j == this.m_goal.getColumnIndex()) {
                    integerObject = 3;
                    b[index] = integerObject.byteValue();
                    index++;
                    continue;
                }
                integerObject = m_maze[i][j];
                b[index] = integerObject.byteValue();
                index++;
            }
            integerObject = -1;
            b[index] = integerObject.byteValue();
            index++;
        }
        return b;
    }

    /**
     * generate maze based on byte array in the format determined
     */
    public Maze(byte[] b) {
        serialNum++;
        this.m_colSize = 0;
        this.m_rowSize = 0;
        Integer newRow = -1, start = 2, goal = 3, zero = 0, one = 1;
        int index = 0;
        boolean isFirstRow = true;
        //finding colSize and rowSize:
        for (int i = 0; i < b.length; i++) {
            if (isFirstRow) {
                m_colSize++;
            }
            if (b[i] == newRow.byteValue()) {
                isFirstRow = false;
                m_rowSize++;
            }

        }
        m_colSize--;
        //initialize int array:
        m_maze = new int[m_rowSize][m_colSize];
        for (int i = 0; i < m_rowSize; i++) {
            for (int j = 0; j < m_colSize; j++) {
                if (b[index] == zero.byteValue() || b[index] == one.byteValue()) {//regular cell (0 or 1)
                    m_maze[i][j] = b[index];
                } else if (b[index] == start.byteValue()) {//start (2)
                    m_maze[i][j] = 0;
                    m_start = new Position(i, j);
                } else if (b[index] == goal.byteValue()) {//goal (3)
                    m_maze[i][j] = 0;
                    m_goal = new Position(i, j);
                } else {//new row (-1)
                    j--;//preventing the j to increment when b[index] is -1
                }
                index++;
            }
        }


    }

    public int hashCode() {

        return Arrays.hashCode(this.toByteArray());
    }

    public String toString() {
        return "-startRow=" + m_start.getRowIndex() +
                "-startCol=" + m_start.getColumnIndex() +
                "-endRow=" + m_goal.getRowIndex() +
                "-endCol=" + m_goal.getColumnIndex()+
                "rowSize"+ m_colSize+
                "-colSize="+m_colSize;
    }
}