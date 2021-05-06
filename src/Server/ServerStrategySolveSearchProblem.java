package Server;

import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Scanner;

public class ServerStrategySolveSearchProblem implements ServerStrategy {
    String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        Solution curSol;
        try {

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
//            MyDecompressorInputStream mdc = new MyDecompressorInputStream(inFromClient);

            Maze maze = (Maze) fromClient.readObject();

            SearchableMaze sm = new SearchableMaze(maze);
            String name = "maze@"+maze.hashCode()+maze.toString();

            boolean found = false;
            File file = new File(tempDirectoryPath);
            File[] files = file.listFiles();
            for (File f:
                    files) {
                if (name.equals(f.getName())){
                    found=true;
                    break;
                }
            }

            if(found){//if maze has been solved before:

                FileInputStream fi = new FileInputStream(new File(tempDirectoryPath+"\\"+name));
                ObjectInputStream oi = new ObjectInputStream(fi);
                curSol = (Solution) oi.readObject();



            }
            else{

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
                curSol =alg.solve(sm);

                //write:
                FileOutputStream f = new FileOutputStream(new File(tempDirectoryPath+"\\"+name));
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(curSol);

            }




            toClient.writeObject(curSol);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getMazeSolutionFileNameFromDirectory(Maze maze){

        String name = "maze@"+maze.hashCode()+maze.toString();
        File file = new File(tempDirectoryPath);
        File[] files = file.listFiles();
        for (File f:
                files) {
            if (name.equals(f.getName())){
                return f.getName();
            }
        }
        return "not solved before";
    }


}
