package algorithms.search;

import algorithms.maze3D.*;
import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest
{
    private Maze maze2D;
    private Maze3D maze3D;
    private SearchableMaze searchableMaze2D;
    private SearchableMaze3D searchableMaze3D;
    private EmptyMazeGenerator emptyMazeGenerator = new EmptyMazeGenerator();
    private SimpleMazeGenerator simpleMazeGenerator = new SimpleMazeGenerator();
    private MyMazeGenerator myMaze2DGenerator = new MyMazeGenerator();
    private MyMaze3DGenerator myMaze3DGenerator = new MyMaze3DGenerator();
    private ISearchingAlgorithm best;

    @Test
    public void GetAlgorithmNameTest()
    {
        best = new BestFirstSearch();
        assertEquals("BestFirstSearch", best.getName());
    }

    @Test
    public void notNullTest()
    {
        assertNull(emptyMazeGenerator.generate(-2, 4));
        assertNull(simpleMazeGenerator.generate(-10, -10));
        assertNull(myMaze2DGenerator.generate(0, 0));
        assertNull(myMaze3DGenerator.generate(4, -9, 10));
    }

    @Test
    public void maze2DSizeTest()
    {
        maze2D = simpleMazeGenerator.generate(100, 200);
        assertEquals(maze2D.getRowSize(), 100);
        assertEquals(maze2D.getColSize(), 200);
    }

    @Test
    public void maze3DSizeTest()
    {
        maze3D = myMaze3DGenerator.generate(20, 34, 57);
        assertEquals(maze3D.getDepthSize(), 20);
        assertEquals(maze3D.getRowSize(), 34);
        assertEquals(maze3D.getColumnSize(), 57);
    }

    @Test
    public void getNumOfNodesEvaluatedTest()
    {
        best = new BestFirstSearch();
        best.solve(null);
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
    }

    @Test
    public void solveMazeTest()
    {
        /* ---------- Empty Maze ---------- */
        solve2DAssert(emptyMazeGenerator, 2, 2);
        solve2DAssert(emptyMazeGenerator, 60, 50);
        solve2DAssert(emptyMazeGenerator, 1000, 1000);

        /* ---------- Simple Maze ---------- */
        solve2DAssert(simpleMazeGenerator, 2, 3);
        solve2DAssert(simpleMazeGenerator, 3, 2);
        solve2DAssert(simpleMazeGenerator, 67, 57);
        solve2DAssert(simpleMazeGenerator, 1000, 650);
        solve2DAssert(simpleMazeGenerator, 1000, 1000);

        /* ----------- My 2D Maze ----------- */
        solve2DAssert(myMaze2DGenerator, 2, 2);
        solve2DAssert(myMaze2DGenerator, 2, 3);
        solve2DAssert(myMaze2DGenerator, 3, 2);
        solve2DAssert(myMaze2DGenerator, 6, 5);
        solve2DAssert(myMaze2DGenerator, 5, 6);
        solve2DAssert(myMaze2DGenerator, 111, 111);
        solve2DAssert(myMaze2DGenerator, 1000, 1000);

        /* ----------- My 3D Maze ----------- */
        solve3DAssert(2, 2, 2);
        solve3DAssert(2, 3, 2);
        solve3DAssert(3, 3, 3);
        solve3DAssert(57, 61, 4);
        solve3DAssert(2, 61, 41);
        solve3DAssert(100, 100, 100);
    }

    private void solve2DAssert(IMazeGenerator mazeGenerator, int row, int column)
    {
        best = new BestFirstSearch();
        maze2D = mazeGenerator.generate(row, column);
        searchableMaze2D = new SearchableMaze(maze2D);
        Solution solution = best.solve(searchableMaze2D);
        ArrayList<AState> solutionPath = solution.getSolutionPath();

        /* Testing if the start position of the maze equals to start position of solution path */
        assert maze2D.getStartPosition().toString().equals(solutionPath.get(0).toString());

        /* Testing if the goal position of the maze equals to goal position of solution path */
        assert maze2D.getGoalPosition().toString().equals(solutionPath.get(solutionPath.size() - 1).toString());

        AState blank = null;
        assert solution != null;
        assert solutionPath.size() > 0;
        assert solutionPath.contains(new MazeState(maze2D.getStartPosition(), 0, blank));
        assert solutionPath.contains(new MazeState(maze2D.getGoalPosition(), 0, blank));
    }

    public void solve3DAssert(int depth, int row, int column)
    {
        best = new BestFirstSearch();
        myMaze3DGenerator = new MyMaze3DGenerator();
        maze3D = myMaze3DGenerator.generate(depth, row, column);
        searchableMaze3D = new SearchableMaze3D(maze3D);
        Solution solution = best.solve(searchableMaze3D);
        ArrayList<AState> solutionPath = solution.getSolutionPath();

        /* Testing if the start position of the maze equals to start position of solution path */
        assert maze3D.getStartPosition().toString().equals(solutionPath.get(0).toString());

        /* Testing if the goal position of the maze equals to goal position of solution path */
        assert maze3D.getGoalPosition().toString().equals(solutionPath.get(solutionPath.size() - 1).toString());

        AState blank = null;
        assert solution != null;
        assert solutionPath.size() > 0;
        assert solutionPath.contains(new Maze3DState(maze3D.getStartPosition(), 0, blank));
        assert solutionPath.contains(new Maze3DState(maze3D.getGoalPosition(), 0, blank));
    }

    @Test
    void solveSimpleMazeInMinute()
    {
        best = new BestFirstSearch();
        simpleMazeGenerator = new SimpleMazeGenerator();
        maze2D = simpleMazeGenerator.generate(1000, 1000);
        searchableMaze2D = new SearchableMaze(maze2D);
        long before = System.currentTimeMillis();
        best.solve(searchableMaze2D);
        long time = System.currentTimeMillis() - before;
        assertTrue(time <= 60000);
    }

    @Test
    void solveMyMaze2DInMinute()
    {
        best = new BestFirstSearch();
        myMaze2DGenerator = new MyMazeGenerator();
        maze2D = myMaze2DGenerator.generate(1000, 1000);
        searchableMaze2D = new SearchableMaze(maze2D);
        long before = System.currentTimeMillis();
        best.solve(searchableMaze2D);
        long time = System.currentTimeMillis() - before;
        assertTrue(time <= 60000);
    }

    @Test
    void solveInMinute3D()
    {
        best = new BestFirstSearch();
        myMaze3DGenerator = new MyMaze3DGenerator();
        maze3D = myMaze3DGenerator.generate(100, 100, 100);
        searchableMaze3D = new SearchableMaze3D(maze3D);
        long before = System.currentTimeMillis();
        best.solve(searchableMaze3D);
        long time = System.currentTimeMillis() - before;
        assertTrue(time <= 60000);
    }
}