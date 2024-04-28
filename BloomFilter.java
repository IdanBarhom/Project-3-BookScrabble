package test;

import java.util.BitSet;
import java.security.MessageDigest;
import java.math.BigInteger;
public class BloomFilter {
    private BitSet bit;
    private MessageDigest md1;
    private MessageDigest md2;
    BloomFilter(int bitsSize,String hash1,String hash2){
        bit=new BitSet(bitsSize);
        try{
            md1=MessageDigest.getInstance(hash1);
        }
        catch (Exception e){
            System.out.println("problem with implementing hash1");
        }
        try{
            md2=MessageDigest.getInstance(hash2);
        }
        catch (Exception e){
            System.out.println("problem with implementing hash2");
        }
    }
        public void add(String word){
            addHash(md1,word);
            addHash(md2,word);
    }
    public void addHash(MessageDigest md,String word){
        byte[] hashBts= md.digest(word.getBytes());
        BigInteger valueObject= new BigInteger(1,hashBts);
        int index=valueObject.intValue()% bit.size();
        if(index<0) index=index*-1;
        bit.set(index);
    }
    public boolean contains(String word){
        return (containHash(md1,word) || containHash(md2,word));
    }
    public boolean containHash(MessageDigest md,String word){
        byte[] hashBts= md.digest(word.getBytes());
        BigInteger valueObject= new BigInteger(1,hashBts);
        int index=valueObject.intValue()% bit.size();
        if(index<0) index=index*-1;
        return (bit.get(index));
    }
    public String toString(){
       StringBuilder bitCode=new StringBuilder();
       for(int index=0;index<bit.length();index++){
            if(bit.get(index))
                bitCode.append('1');
            else
                bitCode.append('0');
       }
       return bitCode.toString();
    }
}
