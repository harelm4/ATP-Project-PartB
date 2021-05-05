package IO;
import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

public class MyCompressorOutputStream extends OutputStream{
    OutputStream out;
    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {
        Integer tmpInt=b;
        byte[] byteArray = new byte[1];
        byteArray[0]=tmpInt.byteValue();
        this.write(byteArray);
    }


    @Override
    public void write(byte[] byteArray) throws IOException {
        //getting sizes and indexes
        int colSize=0,rowSize=0;
        int startRow=0,startCol=0,goalRow=0,goalCol=0;
        int index =0;
        for (byte b:
                byteArray) {
            if(b!=-1){
                colSize++;
            }
            else {
                break;
            }
        }
        for (byte b:
                byteArray) {

            if(b==-1){
                rowSize++;
            }
        }
        int rowIndex=0;
        for (byte b:
                byteArray) {
            if(b==-1){
                rowIndex++;
            }
            if(b==2){
                startCol=(index-rowIndex)%colSize;
                startRow=rowIndex;
            }
            else if(b==3){
                goalCol=(index-rowIndex)%colSize;
                goalRow=rowIndex;
            }
            index++;
        }



        //algorithm:
        int numOfInputBytes= colSize*rowSize;
        int remainBytes = numOfInputBytes%8;
        index =0;
        String curBinary="";
        ArrayList<String> list = new ArrayList<String>();

        for (byte b:
             byteArray) {
            if(b!=-1){
                if(b==3 ||b==2){
                    curBinary+=String.valueOf(0);
                }
                else {
                    curBinary+=String.valueOf(b);
                }

                index++;
                if(index%8==0 || index==numOfInputBytes){
                    list.add(curBinary);
                    curBinary="";
                }
            }
        }
        index=0;
        byte[] finalArray = new byte[list.size()+24];
        for (String s:
             list) {
            finalArray[index]=(byte) Integer.parseInt(s,2);
            index++;
        }
        //rowSize,colSize,startRow,startCol,goalrow,goalCol
        int[] arr = new int[]{rowSize,colSize,startRow,startCol,goalRow,goalCol};
        index=0;
        for (int i=finalArray.length-24;i<finalArray.length-1;i=i+4) {
            byte[] converted = bigIntToByteArray(arr[index]);
            for(int j=0;j<4;j++){
                finalArray[i+j]=converted[j];
            }
            index++;

        }
        for (byte b:
             finalArray) {
            out.write(b);
        }


    }

    private byte[] bigIntToByteArray( final int i ) {
        ByteBuffer b = ByteBuffer.allocate(4);

        b.putInt(i);

        return b.array();
    }
    private byte setBit(byte _byte,int bitPosition,boolean bitValue)
    {
        if (bitValue)
            return (byte) (_byte | (1 << bitPosition));
        return (byte) (_byte & ~(1 << bitPosition));
    }
    }


