package Server;

import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ServerStrategySolveSearchProblem implements ServerStrategy {

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
//            MyDecompressorInputStream mdc = new MyDecompressorInputStream(inFromClient);
            Maze maze = (Maze) fromClient.readObject();
            SearchableMaze sm = new SearchableMaze(maze);
            ISearchingAlgorithm alg;
            if (Configurations.getInstance().getMazeSearchingAlgorithm().equals("BFS")){
                alg=new BestFirstSearch();
            }
            else if (Configurations.getInstance().getMazeSearchingAlgorithm().equals("DFS")){
                alg=new DepthFirstSearch();
            }
            else{
                alg=new BestFirstSearch();
            }
            Solution curSol =alg.solve(sm);
            toClient.writeObject(curSol);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
