package IO;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    public MyDecompressorInputStream(InputStream in){
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
        //finding rowSize,colSize,startRow,startCol,goalRow,goalCol,digits:
        //digits
        byte[] digitsArray = new byte[4];
        int index=3;
        for(int i=inputByteArray.length-1;i>inputByteArray.length-4;i--){
            digitsArray[index] = inputByteArray[i];
            index--;
        }
        int digits = convertByteArrayToInt(digitsArray);
        //goalCol
        byte[] goalColArray = new byte[4];
        index=3;
        for(int i=inputByteArray.length-5;i>inputByteArray.length-9;i--){
            goalColArray[index] = inputByteArray[i];
            index--;
        }
        int goalCol = convertByteArrayToInt(goalColArray);
        //goalRow
        byte[] goalRowArray = new byte[4];
        index=3;
        for(int i=inputByteArray.length-9;i>inputByteArray.length-13;i--){
            goalRowArray[index] = inputByteArray[i];
            index--;
        }
        int goalRow = convertByteArrayToInt(goalRowArray);
        //startCol
        byte[] startColArray = new byte[4];
        index=3;
        for(int i=inputByteArray.length-13;i>inputByteArray.length-17;i--){
            startColArray[index] = inputByteArray[i];
            index--;
        }
        int startCol = convertByteArrayToInt(startColArray);
        //startRow
        byte[] startRowArray = new byte[4];
        index=3;
        for(int i=inputByteArray.length-17;i>inputByteArray.length-21;i--){
            startRowArray[index] = inputByteArray[i];
            index--;
        }
        int startRow = convertByteArrayToInt(startRowArray);
        //colSize
        byte[] colSizeArray = new byte[4];
        index=3;
        for(int i=inputByteArray.length-21;i>inputByteArray.length-25;i--){
            colSizeArray[index] = inputByteArray[i];
            index--;
        }
        int colSize = convertByteArrayToInt(colSizeArray);
        //rowSize
        byte[] rowSizeArray = new byte[4];
        index=3;
        for(int i=inputByteArray.length-25;i>inputByteArray.length-29;i--){
            rowSizeArray[index] = inputByteArray[i];
            index--;
        }
        int rowSize = convertByteArrayToInt(rowSizeArray);



        //finding baseList
        ArrayList<BBPair> inputList = new ArrayList<BBPair>();
        int j;
        for(int i=0 ;i<inputByteArray.length-4*7;i=i+digits+1){
            byte[] numberArray = new byte[digits];

            for(j=0 ; j<digits;j++){
                numberArray[j] = inputByteArray[i+j];
            }

            BBPair kp =new BBPair(numberArray,inputByteArray[i+j]);
            inputList.add(kp);
        }
        index=1;
        HashMap<String,String> mapP2N = new HashMap<String,String>();
        HashMap<String,String> mapN2P = new HashMap<String,String>();
        String sLeft="";
        ArrayList<String> list = new ArrayList<String>();

        //decoding
        for (BBPair kp:
             inputList) {
            String prefix = String.valueOf(convertBinaryToInt(kp.bArr));
            if(prefix.equals("0")){
                prefix="";
            }
            if(!mapN2P.containsKey(prefix)){
                mapN2P.put(String.valueOf(index),prefix+kp.b);
                mapP2N.put(prefix+kp.b,String.valueOf(index));
                list.add(prefix+kp.b);
            }
            else{
                list.add(mapN2P.get(prefix)+kp.b);
                mapN2P.put(String.valueOf(index),mapN2P.get(prefix)+kp.b);
                mapP2N.put(mapN2P.get(prefix)+kp.b,String.valueOf(index));
            }
            index++;
        }
        ArrayList<Byte> baseList = new ArrayList<Byte>();
        for (String s:
             list) {
            for (char c:
                 s.toCharArray()) {
                baseList.add(Byte.valueOf(String.valueOf(c)));
            }
        }
        ArrayList<Byte> finalList = new ArrayList<Byte>();
        int rowNum=0;
        for (int i=0 ; i<baseList.size();i++){
            if(i%colSize==0 && i!=0){
                finalList.add((byte) -1);
                rowNum++;
            }
            if(startCol==(i)%colSize && startRow==rowNum){
                finalList.add((byte) 2);

            }
            else if(goalCol==(i)%colSize && goalRow==rowNum){
                finalList.add((byte) 3);
            }
            else{
                finalList.add(baseList.get(i));
            }

        }
        finalList.add((byte) -1);

        index=0;
        for (Byte b:
             finalList) {
            returnArray[index]=finalList.get(index);
            index++;
        }

        return in.read(returnArray);
    }
    private int convertByteArrayToInt(byte[] intBytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
        return byteBuffer.getInt();
    }
    private int convertBinaryToInt(byte[] bytes){
        int exp=0;
        int result=0;
        for(int i=bytes.length-1;i>=0;i--){
            int left=bytes[i];
            int right = (int) Math.pow(2,exp);
            result+=left*right;
            exp++;
        }
        return result;
    }
    private class BBPair{
        public byte[] bArr;
        public byte b;
        BBPair(byte[] a,byte b){
            this.b =b;
            this.bArr=a;
        }
    }
}
