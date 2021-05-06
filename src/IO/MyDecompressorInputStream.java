package IO;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;


public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    public MyDecompressorInputStream(InputStream in) {
        this.in=in;
    }
    @Override
    public int read() throws IOException {
        return in.read();
    }
    @Override
    public int read(byte[] returnArray) throws IOException {
        byte[] inputArray = new byte[in.available()];
        in.read(inputArray);

        //rowSize,colSize,startRow,startCol,goalrow,goalCol
        //indexes and sizes
        byte[] rowSizeB=new byte[4];
        byte[] colSizeB=new byte[4];
        byte[] startRowB=new byte[4];
        byte[] startColB=new byte[4];
        byte[] goalRowB=new byte[4];
        byte[] goalColB=new byte[4];
        int index=inputArray.length-4*6;
        for(int i=0;i<4;i++){
            rowSizeB[i]=inputArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            colSizeB[i]=inputArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            startRowB[i]=inputArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            startColB[i]=inputArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            goalRowB[i]=inputArray[index];
            index++;
        }
        for(int i=0;i<4;i++){
            goalColB[i]=inputArray[index];
            index++;
        }
        int rowSize =convertByteArrayToInt(rowSizeB);
        int colSize =convertByteArrayToInt(colSizeB);
        int startRow =convertByteArrayToInt(startRowB);
        int startCol =convertByteArrayToInt(startColB);
        int goalRow =convertByteArrayToInt(goalRowB);
        int goalCol =convertByteArrayToInt(goalColB);

        //decompressing data
        String tmpString="";
        ArrayList<String > list = new ArrayList<String>();
        for(int i=0 ;i<inputArray.length-24;i++){
            tmpString=String.format("%8s", Integer.toBinaryString(inputArray[i] & 0xFF)).replace(' ', '0');
            int length = tmpString.length();
            for(int j=0;j<8-length;j++){
                tmpString="0"+tmpString;
            }
            list.add(tmpString);
            tmpString="";
        }
        int numOfInputBytes= colSize*rowSize;
        int remainBits = numOfInputBytes%8;
        int rowNum=0;
        index=0;
        int sIndex=0,debugIndex=0;
        int kk=0;





        for (String s:
             list) {
            debugIndex++;
            //reminder byte:
            if(debugIndex==list.size() && remainBits!=0){


                char[] chars = s.toCharArray();
                for(int i=s.length()-remainBits;i<s.length()+1;i++){
                    if((index-rowNum)%colSize==0 && index!=0 && index!=returnArray.length){
                        returnArray[index]=-1;
                        rowNum++;
                        index++;
                        if(index==returnArray.length){
                            break;
                        }

                    }
                    if(rowNum==startRow && (index-rowNum)%colSize==startCol ){
                        returnArray[index]=Byte.valueOf("2");
                        index++;

                    }
                    else if(rowNum==goalRow && (index-rowNum)%colSize==goalCol ){
                        returnArray[index]=Byte.valueOf("3");
                        index++;

                    }
                    else  {
                        char c;
                        c= chars[i];
                        returnArray[index]=Byte.valueOf(String.valueOf(c));

                        index++;

                    }

                    if(index==returnArray.length){

                        break;
                    }
                }
            }

            //all bytes except reminder:
            if(index==returnArray.length){
                break;
            }
            for (char c:
                 s.toCharArray()) {

                if(index==returnArray.length){
                    break;
                }
                if((index-rowNum)%colSize==0 && index!=0 && sIndex!=0){
                    returnArray[index]=-1;
                    rowNum++;
                    index++;

                }
                //for each input bit:
                if(index!=returnArray.length){
                    if(rowNum==startRow && (index-rowNum)%colSize==startCol ){
                        returnArray[index]=Byte.valueOf("2");
                        index++;
                        sIndex++;

                    }
                    else if(rowNum==goalRow && (index-rowNum)%colSize==goalCol ){
                        returnArray[index]=Byte.valueOf("3");
                        index++;
                        sIndex++;

                    }
                    else{
                        returnArray[index]=Byte.valueOf(String.valueOf(c));
                        index++;
                        sIndex++;
                    }

                }


            }
            sIndex=0;
            if((index-rowNum)%colSize==0 && index!=0 && index!=returnArray.length){
                returnArray[index]=-1;
                rowNum++;
                index++;
                if(index==returnArray.length){
                    break;
                }
            }





        }

        return in.read(returnArray);

    }
    private int convertByteArrayToInt(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getInt();
    }
    static String decimalToBinary(int num)
    {
        // Creating Stack Object Vector
        Stack<Integer> st = new Stack<>();

        // Number Should be positive
        while (num > 0) {

            // Pushing numbers inside stack that
            // are divisible by 2
            st.push(num % 2);
            // Dividing number by 2
            num = num / 2;
        }

        // Checking condition whether stack is empty
        String tmp ="";
        while (!(st.isEmpty())) {

            // Printing binary number
            tmp+=st.pop();
        }
        return tmp;
    }


}
