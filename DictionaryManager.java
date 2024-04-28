package test;


import java.util.*;


public class DictionaryManager  {
    public Map<String, Dictionary> books;
    public static DictionaryManager  dm;
    DictionaryManager(){
        books=new HashMap<>();
    }

    public static DictionaryManager get(){
        if(dm==null){
            dm=new DictionaryManager();
        }
        return dm;
    }
    public boolean query(String...args)
    {
        String word=args[args.length-1];
        int i=0;
        while(i<args.length-1){
            String file=args[i];
            if(books.containsKey(args[i])){
                if(books.get(file).query(word))
                    return true;
            }
            else{
                books.put(args[i],new Dictionary(file));
                if(books.get(file).query(word))
                    return true;
            }
            i++;
        }
        return false;
    }
    public boolean challenge(String...args)
    {

        String word=args[args.length-1];
        int i=0;
        while(i<args.length-1){
            if(books.get(args[i]).challenge(args[i],word))
                return true;
            i++;
        }
        return false;
    }
    public int getSize(){
        return books.size();
    }
}

