package test;
import java.io.*;
public class IOSearcher {

    public static boolean search(String...args){
        String word=args[args.length-1];
        //String[] files=new String[args.length-1];

        //System.arraycopy(args,1,files,0,files.length);

        int i=0;
        while(i<args.length-1){
            if(specificSearch(word,args[i]))
                return true;
            i++;
        }
        return false;

    }
    public static boolean specificSearch(String word, String file){

        try {
            BufferedReader read= new BufferedReader(new FileReader(file));
            String line;
            while ((line=read.readLine())!=null) {

                String[] words=line.split(" ");

                for(String string: words){
                    if(string.equals(word))
                        return true;
                }
            }
            read.close();
        }
        catch(Exception e){
            System.out.println("there is an error"+e);
        }
        return false;
    }
}
