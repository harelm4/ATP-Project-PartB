package IO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;


/**
 * based on the algorithm in page 19
 */
public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;
    public SimpleDecompressorInputStream(InputStream in){
        this.in=in;
    }
    @Override
    public int read() throws IOException {
        return in.read();
    }
    @Override
    public int read(byte[] returnArray) throws IOException {
        byte[] inputByteArray= new byte[in.available()];
        in.read(inputByteArray);
        for (byte b:
             inputByteArray) {
            System.out.printf(String.valueOf(b)+ "\n");
        }
        int index;
        //getting :maze row size,maze column size,start row index,start column index,goal row index,goal column index
        byte[] rowSizeB=new byte[4];
        byte[] colSizeB=new byte[4];
        byte[] startRowB=new byte[4];
        byte[] startColB=new byte[4];
        byte[] goalRowB=new byte[4];
        byte[] goalColB=new byte[4];
        index=inputByteArray.length-4*6;
        for(int i=0;i<4;i++){
            rowSizeB[i]=inputByteArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            colSizeB[i]=inputByteArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            startRowB[i]=inputByteArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            startColB[i]=inputByteArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            goalRowB[i]=inputByteArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            goalColB[i]=inputByteArray[index];
            index++;
        }
       int rowSize =convertByteArrayToInt(rowSizeB);
        int colSize =convertByteArrayToInt(colSizeB);
        int startRow =convertByteArrayToInt(startRowB);
        int startCol =convertByteArrayToInt(startColB);
        int goalRow =convertByteArrayToInt(goalRowB);
        int goalCol =convertByteArrayToInt(goalColB);

        //build the result byte array

        ArrayList<Byte> bytesList= new ArrayList<Byte>();
        byte curVal=0;
        int bytesAdded=0,rowIndex=0,colIndex=0;
        byte mOne=-1,start=2,goal=3;
        for (byte b:
             inputByteArray) {
            for(int i=0;i<b;i++){
                if(bytesAdded%colSize==0 && !bytesList.isEmpty()){
                    bytesList.add(mOne);
                    rowIndex++;
                }
                if (rowIndex==startRow && colIndex==startCol){
                    bytesList.add(start);
                }
                else if(rowIndex==goalRow && colIndex==goalCol){
                    bytesList.add(goal);
                }
                else{
                    bytesList.add(curVal);
                }

                bytesAdded++;//count of all non -1 bytes in the list
                colIndex = (bytesAdded)%colSize;
        }
            curVal=changeVal(curVal);
        }
        bytesList.add(mOne);

        for(int i=0;i<returnArray.length;i++){
            returnArray[i]=bytesList.get(i);
        }
        return in.read(returnArray);


    }
    private int convertByteArrayToInt(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getInt();
    }
    private byte changeVal(byte b){
        if (b==0){
            return 1;
        }
        else{
            return 0;
        }
    }
}
