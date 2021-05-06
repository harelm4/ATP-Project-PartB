package test;

import Client.Client;
import Server.Server;
import Server.ServerStrategySolveSearchProblem;
import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.Solution;
import Server.Configurations;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

public class myTests {
    public static void main(String[] args) throws UnknownHostException {
        Configurations.getInstance().setMazeSearchingAlgorithm("BFS");
        Configurations.getInstance().setMazeGeneratingAlgorithm("MyMazeGaenerator");
        Configurations.getInstance().setThreadPoolSize("3");
        Server s = new Server(4000, 1000, new ServerStrategySolveSearchProblem());
        s.start();
        MyMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000, 1000);
        CommunicateWithServer_SolveSearchProblem(maze);
        Configurations.getInstance().setMazeSearchingAlgorithm("DFS");
        CommunicateWithServer_SolveSearchProblem(maze);

        s.stop();
    }

    private static void CommunicateWithServer_SolveSearchProblem(Maze maze) {
        try {
                Client c = new Client(InetAddress.getLocalHost(), 4000,
                        new IClientStrategy() {
                            @Override
                            public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {


                                try {
                                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                    toServer.flush();
                                    toServer.writeObject(maze); //send maze to server
                                    toServer.flush();
                                    Solution mazeSolution = (Solution) fromServer.readObject();
                                    System.out.printf(mazeSolution.toString() + "\n");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                );
            c.communicateWithServer();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }

}