package test;


import java.util.LinkedHashMap;
import java.util.Map;

public class LRU implements CacheReplacementPolicy{

    public static LinkedHashMap<String,Long> linkedMap;

    public LRU(){
        linkedMap=new LinkedHashMap<String,Long>();

    }
    @Override
    public void add(String word){
        linkedMap.remove(word);
        linkedMap.put(word,System.nanoTime());
    }
    @Override
    public String remove(){
      if(linkedMap.isEmpty())
          return null;
      String wordToBeRemoved=linkedMap.entrySet().iterator().next().getKey();
      linkedMap.remove(wordToBeRemoved);
      return wordToBeRemoved;
    }
}
