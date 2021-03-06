package Server;

import algorithms.mazeGenerators.IMazeGenerator;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public  class Configurations {
    static Configurations obj;
    private Properties prop;
    private Configurations(){
          prop = new Properties();

    }
    public static synchronized Configurations getInstance(){
        if(obj==null){
            obj=new Configurations();
        }
        return obj;
    }
    public synchronized void setThreadPoolSize(String size){
        try{
            prop.setProperty("threadPoolSize",size);
            prop.store(new FileOutputStream("config.properties"),null);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public synchronized void setMazeGeneratingAlgorithm(String mazeGeneratingAlgorithm){
        try{
            prop.setProperty("mazeGeneratingAlgorithm",mazeGeneratingAlgorithm);
            prop.store(new FileOutputStream("config.properties"),null);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public synchronized void setMazeSearchingAlgorithm(String searchingAlgorithm) {
        try {
            prop.setProperty("mazeSearchingAlgorithm", searchingAlgorithm);
            prop.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized String getThreadPoolSize(){
        String value="";
        try {
            prop.load(new FileInputStream("config.properties"));
            value=prop.getProperty("threadPoolSize");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    public synchronized String getMazeGeneratingAlgorithm(){
        String value="";
        try {
            prop.load(new FileInputStream("config.properties"));
            value=prop.getProperty("mazeGeneratingAlgorithm");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    public synchronized String getMazeSearchingAlgorithm(){
        String value="";
        try {
            prop.load(new FileInputStream("config.properties"));
            value=prop.getProperty("mazeSearchingAlgorithm");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
