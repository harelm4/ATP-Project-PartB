package test;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(3,100); //Generate new maze
//        Maze maze = new Maze(new byte[]{new Integer(0).byteValue(), new Integer(2).byteValue(), new Integer(0).byteValue(), new Integer(-1).byteValue(),
//                new Integer(1).byteValue(), new Integer(0).byteValue(), new Integer(1).byteValue(), new Integer(-1).byteValue(),
//                new Integer(0).byteValue(), new Integer(0).byteValue(), new Integer(0).byteValue(), new Integer(-1).byteValue(),
//                new Integer(1).byteValue(), new Integer(3).byteValue(), new Integer(1).byteValue(), new Integer(-1).byteValue()});
        try {
// save maze to a file
            byte[] mba=maze.toByteArray();
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        try {
//read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
//            maze.print();
            int index=0;
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        Maze loadedMaze = new Maze(savedMazeBytes);
//
//        for(int i=0 ; i<loadedMaze.toByteArray().length;i++){
//            if(loadedMaze.toByteArray()[i] != maze.toByteArray()[i]){
//                System.out.printf(i/8+" ");
//            }
//        }
//        System.out.printf("\n");
        boolean areMazesEquals =
                Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
//        int index=0;
//        if(!areMazesEquals){
//            for (byte b:
//                    maze.toByteArray()) {
//                if(index%8==0){
//                    System.out.printf("|");
//                }
//                index++;
//                System.out.printf(b +" ");
//
//            }
//            index=0;
//            System.out.printf("\n");
//            for (byte b:
//                    loadedMaze.toByteArray()) {
//                if(index%8==0){
//                    System.out.printf("|");
//                }
//                index++;
//                System.out.printf(b +" ");
//
//            }
//            System.out.printf("\n");
//
//
//        }
//        index=0;
//        for (byte b:
//                loadedMaze.toByteArray()) {
//            if(index%8==0){
//                System.out.printf(index/8+"");
//            }
//            System.out.printf("  ");
//            index++;
//
//
//        }
//        System.out.printf("\n");
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
//maze should be equal to loadedMaze
    }
}
