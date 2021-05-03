package algorithms.maze3D;

/**
 * The maze is represented as a 3D char array.
 * Zero ('0') - Pass
 * One ('1') - Wall
 * Start ('S') - The start position
 * End ('E') - The goal position
 *
 * @author Eden_Hai
 * @version 1.0
 * @since 31-03-2021
 */
public class Maze3D
{
    private int[][][] m_maze;
    private int m_rowSize;
    private int m_colSize;
    private int m_depthSize;
    private Position3D m_start;
    private Position3D m_goal;

    public Maze3D(int[][][] maze, Position3D start, Position3D goal)
    {
        this.m_maze = maze;
        this.m_start = start;
        this.m_goal = goal;
        this.m_depthSize = maze.length;
        this.m_rowSize = maze[0].length;
        this.m_colSize = maze[0][0].length;
    }

    public int getDepthSize() { return m_depthSize; }

    public int getRowSize() { return m_rowSize; }

    public int getColumnSize() { return m_colSize; }

    public int[][][] getMap() { return m_maze; }

    public Position3D getStartPosition() { return m_start; }

    public Position3D getGoalPosition() { return m_goal; }

    public void print()
    {
        System.out.println("{");
        for (int depth = 0; depth < m_depthSize; depth++)
        {
            for (int row = 0; row < m_rowSize; row++)
            {
                System.out.print("{ ");
                for (int col = 0; col < m_colSize; col++)
                {
                    /* if the position is the start - mark with S */
                    if (depth == m_start.getDepthIndex() && row == m_start.getRowIndex() && col == m_start.getColumnIndex())
                    { System.out.print("S "); }

                    /* if the position is the goal - mark with E */
                    else if (depth == m_goal.getDepthIndex() && row == m_goal.getRowIndex() && col == m_goal.getColumnIndex())
                    { System.out.print("E "); }

                    else { System.out.print(m_maze[depth][row][col] + " "); }
                }
                System.out.println("}");
            }
            if (depth < m_depthSize - 1)
            {
                System.out.print("---");
                for (int i = 0; i < m_colSize; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }
}