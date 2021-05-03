package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements ServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            IMazeGenerator mg;
            int[] arr = (int[])fromClient.readObject();
            if(Configurations.getInstance().getMazeGeneratingAlgorithm().equals("EmptyMazeGenerator")){
                mg = new EmptyMazeGenerator();
            }
            else if(Configurations.getInstance().getMazeGeneratingAlgorithm().equals("MyMazeGenerator")){
                mg = new MyMazeGenerator();
            }
            else {
                mg=new SimpleMazeGenerator();
            }

            Maze maze = mg.generate(arr[0],arr[1]);

            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            OutputStream mc = new MyCompressorOutputStream(ba);
            mc.write(maze.toByteArray());
            toClient.writeObject(ba.toByteArray());
            toClient.flush();
//            toClient.flush();
//            mc.write(maze.toByteArray());

//            mc.flush();
//            fromClient.close();
//            toClient.close();
        }
        catch (Exception e){
            System.out.printf("exception from ServerStrategyGenerateMaze:\n ");
            e.printStackTrace();
        }

    }
}
