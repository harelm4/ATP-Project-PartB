package Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private ServerStrategy strategy;
    private boolean stop;
    private ExecutorService threadPool; // Thread pool
    //HashMap<byte[],File> solutions;

    public Server(int port, int listeningIntervalMS, ServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        // initialize a new fixed thread pool with 2 threads:
        this.threadPool = Executors.newFixedThreadPool(Integer.valueOf(Configurations.getInstance().getThreadPoolSize()));
        //solutions=new HashMap<byte[],File>();
    }
    public void start(){
        new Thread(()->{
            runServer();
        }).start();
    }
    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();


                    // Insert the new task into the thread pool:
                    threadPool.execute(() -> {
                        handleClient(clientSocket);
                    });


                } catch (SocketTimeoutException e){
                    e.printStackTrace();

                }


//                serverSocket.close();

            }
            serverSocket.close();
            threadPool.shutdown(); // do not allow any new tasks into the thread pool (not doing anything to the current tasks and running threads)
          //  threadPool.shutdownNow(); // do not allow any new tasks into the thread pool, and also interrupts all running threads (do not terminate the threads, so if they do not handle interrupts properly, they could never stop...)
        } catch (IOException e) {
            System.out.printf("Thread num:"+Thread.currentThread().getName()+"\n");
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
    private synchronized void handleClient(Socket clientSocket) {
        try {

            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());

            clientSocket.close();
//            byte[] bytes = new byte[clientSocket.getInputStream().available()];
//            clientSocket.getInputStream().read(bytes);
//
//            if(solutions.containsKey(bytes)){
//                ObjectOutputStream toClient = new ObjectOutputStream(clientSocket.getOutputStream());
//                toClient.writeObject(solutions.get(bytes));
//            }
//            else{
//                strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//                solutions.get(bytes,)
//            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
//        System.out.printf("Server "+this.port+" Stopped\n");
        this.stop = true;
    }

}
