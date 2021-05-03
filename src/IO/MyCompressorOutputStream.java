package IO;
import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

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
    public void write(byte[] inputByteArray) throws IOException {
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        int rowSize = 1, colSize = 0, startRow = 0, startCol = 0, goalRow = 0, goalCol = 0;
        Integer newRow = -1, start = 2, goal = 3, zero = 0, one = 1;
        //getting row and col size
        for (byte b :
                inputByteArray) {
            if (b != newRow) {
                colSize++;
            } else {
                break;
            }
        }

        //creating a list without -1s
        ArrayList<String> baseList = new ArrayList<String>();
        ArrayList<String> toSend = new ArrayList<String>();
        int j = 0;
        for (byte b :
                inputByteArray) {

            if (b != newRow) {
                if (b == 2) {
                    baseList.add(Byte.toString(Byte.valueOf("0")));
                    startCol = (j - rowSize + 1) % colSize;
                    startRow = rowSize - 1;
                } else if (b == 3) {
                    baseList.add(Byte.toString(Byte.valueOf("0")));
                    //setting goal column index
                    if (inputByteArray[j + 1] == -1) {
                        goalCol = colSize - 1;
                    } else {
                        goalCol = (j - rowSize + 1) % colSize;
                    }
                    //setting goal row index
                    goalRow = rowSize - 1;
                } else if (b == 1) {
                    baseList.add("1");
                } else {
                    baseList.add("0");
                }
            } else {
                rowSize++;
            }
            j++;
        }

        HashMap<String,String> mapP2I = new HashMap<String,String>();
        HashMap<String,String> mapI2P = new HashMap<String,String>();//pm2
        HashMap<String,String> stringToPrefix = new HashMap<String,String>();//pm2
        HashMap<String,String> stringToSuffix = new HashMap<String,String>();//pm2
        ArrayList<String> strings = new ArrayList<String>();
        rowSize--;
        //lempel-Ziv:
        String p = "", c = "";
        int index = 1;
        for (int i = 0; i < baseList.size(); i++) {
            c = baseList.get(i);
            if (!mapP2I.containsKey(p + c)) {
                mapP2I.put(p+c,String.valueOf(index));
                mapI2P.put(String.valueOf(index),p+c);
                if(p.equals("")){
                    strings.add("0"+c);
                    stringToPrefix.put("0"+c,"0");
                    stringToSuffix.put("0"+c,c);
                }
                else{
                    strings.add(mapP2I.get(p)+c);
                    stringToPrefix.put(mapP2I.get(p)+c,mapP2I.get(p));
                    stringToSuffix.put(mapP2I.get(p)+c,c);
                }



                index++;
                p="";
            }
            else{
                if(i==baseList.size()-1){
                    mapP2I.put(p+c,String.valueOf(index));
                    mapI2P.put(String.valueOf(index),p+c);
                    if(p.equals("")){
                        strings.add("0"+c);
                        stringToPrefix.put("0"+c,"0");
                        stringToSuffix.put("0"+c,c);
                    }
                    else{
                        strings.add(mapP2I.get(p)+c);
                        stringToPrefix.put(mapP2I.get(p)+c,mapP2I.get(p));
                        stringToSuffix.put(mapP2I.get(p)+c,c);
                    }
                }
                p=p+c;

            }
        }

            int digits = (int) Math.ceil(log2(strings.size()))+1;

        String tmp="";
        LinkedList<String> tmpList = new LinkedList<String>();
        String tmpB;
        int prefix,Bindex;
        for (String s:
             strings) {
            tmp="";
            tmpList.removeAll(tmpList);
            prefix=Integer.valueOf(stringToPrefix.get(s));
            tmpB= Integer.toBinaryString(prefix);
            Bindex=tmpB.length()-1;
            for(int i=digits ;i>0;i--){
                if(Bindex<0){
                    tmpList.addFirst(String.valueOf(0));
                }
                else{
                    tmpList.addFirst(String.valueOf(tmpB.charAt(Bindex)));
                    Bindex--;
                }

            }
            for (String b:
                 tmpList) {
                tmp+=b;
            }
            toSend.add(tmp+stringToSuffix.get(s));

        }

            //writing body:

            for (String s :
                    toSend) {
                byte[] justForWrite = new byte[s.length()];
                for(int i=0;i<s.length();i++){
                    justForWrite[i]=Byte.valueOf(String.valueOf(s.charAt(i)));

                }
                out.write(justForWrite);
            }

            //writing tail of byteArray:
            int[] end = new int[]{rowSize,colSize,startRow,startCol,goalRow,goalCol,digits};
            byte[] BA;//desegnated to convert int to byte array
            byte[] tmpArray = new byte[4];// desegnated for containing final result
        for (int num:
             end) {
            BA =intToByteArray(num);
            Bindex=BA.length-1;
            tmpList.removeAll(tmpList);
            for (int i = 4; i > 0; i--) {
                if (Bindex < 0) {
                    tmpList.addFirst(String.valueOf(0));
                } else {
                    tmpList.addFirst(String.valueOf(BA[Bindex]));
                    Bindex--;
                }
            }

            for (int i = 0; i<4; i++) {
                tmpArray[i] = Byte.valueOf(tmpList.get(i));
            }

            out.write(tmpArray);

        }
//        System.out.printf("Done compressing and writing\n");






        }

    private byte[] intToByteArray( final int i ) {
        BigInteger bigInt = BigInteger.valueOf(i);
        return bigInt.toByteArray();
    }
    private static int log2(int N)
    {

        // calculate log2 N indirectly
        // using log() method
        int result = (int)(Math.log(N) / Math.log(2));

        return result;
    }


    }


