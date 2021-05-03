package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;
    public SimpleCompressorOutputStream(OutputStream o){
        out=o;
    }
    @Override
    public void write(int b) throws IOException {
        Integer tmpInt=b;
        byte[] byteArray = new byte[1];
        byteArray[0]=tmpInt.byteValue();
        this.write(byteArray);
    }
    @Override
    /**
     * check if b[i] equals to b[i-1] -
     *      if so, count the repetitions
     *      else, use "out" to write this byte
     *  repeat for each byte
     */
    public void write(byte[] byteArray) throws IOException {
        int rowSize=1,colSize=0,index=0,startRow=0,startCol=0,goalRow=0,goalCol=0;
        ArrayList<Byte> list = new ArrayList<Byte>();
        byte counter=0;
        Integer currentValue=0;
        Integer  newRow=-1,start=2,goal=3,zero=0,one=1;

        //finding column size
        for (byte b:
                byteArray) {
            if(b!=newRow){
                colSize++;
            }
            else {
                break;
            }
        }
        //setting the to be sent byteArray
        for (byte b:
             byteArray) {
                //handle 0 or 1
                if (b==0 || b==1){
                    if (currentValue.byteValue()==b){
                        if(counter<255){
                            counter++;
                        }
                        else{
                            list.add(counter);
                            list.add(zero.byteValue());
                            counter=1;

                        }
                    }
                    else{
                        currentValue=changeVal(currentValue);
                        list.add(counter);
                        counter=1;


                    }


            }
                //handle start position
            else if(b==start){
                if(currentValue==1){
                    currentValue=0;
                    list.add(counter);
                    counter =1;
                    startCol=(index-rowSize+1)%colSize;
                    startRow=rowSize-1;
                }
                else{
                    counter++;
                    //setting start column index
                    if(byteArray[index+1]==-1){
                        startCol=colSize-1;
                    }
                    else{
                        startCol=(index-rowSize+1)%colSize;
                    }
                    //setting start row index
                    startRow=rowSize-1;

                }
            }
            //handle goal position
            else if (b==goal){
                if(currentValue==1){
                    currentValue=0;
                    list.add(counter);
                    counter =1;
                    goalCol=(index-rowSize+1)%colSize;
                    goalRow=rowSize-1;
                }
                else{
                    counter++;
                    //setting goal column index
                    if(byteArray[index+1]==-1){
                        goalCol=colSize-1;
                    }
                    else{
                        goalCol=(index-rowSize+1)%colSize;
                    }
                    //setting goal row index
                    goalRow=rowSize-1;
                }

            }
            //handle new row
            else if(b==newRow){
                rowSize++;
            }

            index++;

        }
        rowSize--;
        list.add(counter);
        //the end of this array is:
        //maze row size,maze column size,start row index,start column index,goal row index,goal column index
        byte[] newByteArray = new byte[list.size() + 4*6]; //4 bytes times the end of the array
        index=0;
        for (Byte b:
             list) {
            newByteArray[index]=list.get(index);
            index++;
        }
        byte[] BstartRow =  ByteBuffer.allocate(4).putInt(startRow).array(),BstartCol=ByteBuffer.allocate(4).putInt(startCol).array(),
                BgoalRow=ByteBuffer.allocate(4).putInt(goalRow).array(),BgoalCol=ByteBuffer.allocate(4).putInt(goalCol).array(),
                BrowSize =ByteBuffer.allocate(4).putInt(rowSize).array(),BcolSize = ByteBuffer.allocate(4).putInt(colSize).array();
        byte[] byteArrayToInsert=BrowSize;
        int indexJ=0;
        for (byte b:
             byteArrayToInsert) {
            newByteArray[index]=byteArrayToInsert[indexJ];
            index++;
            indexJ++;
        }
        byteArrayToInsert=BcolSize;
        indexJ=0;
        for (byte b:
                byteArrayToInsert) {
            newByteArray[index]=byteArrayToInsert[indexJ];
            index++;
            indexJ++;
        }
        byteArrayToInsert=BstartRow;
        indexJ=0;
        for (byte b:
                byteArrayToInsert) {
            newByteArray[index]=byteArrayToInsert[indexJ];
            index++;
            indexJ++;
        }
        byteArrayToInsert=BstartCol;
        indexJ=0;
        for (byte b:
                byteArrayToInsert) {
            newByteArray[index]=byteArrayToInsert[indexJ];
            index++;
            indexJ++;
        }
        byteArrayToInsert=BgoalRow;
        indexJ=0;
        for (byte b:
                byteArrayToInsert) {
            newByteArray[index]=byteArrayToInsert[indexJ];
            index++;
            indexJ++;
        }
        byteArrayToInsert=BgoalCol;
        indexJ=0;
        for (byte b:
                byteArrayToInsert) {
            newByteArray[index]=byteArrayToInsert[indexJ];
            index++;
            indexJ++;
        }
        for (byte b:
             newByteArray) {
            out.write(b);
        }
    }

    /**
     switch between 0->1 and 1->0
     */
    private int changeVal(Integer integer){
        if (integer==0){
            return 1;
        }
        else{
            return 0;
        }
    }
}
