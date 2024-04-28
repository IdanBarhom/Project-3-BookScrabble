package test;


import java.util.HashSet;

public class CacheManager{
    public int capacity;
    public CacheReplacementPolicy crp;
    public HashSet<String> cache;

    public CacheManager(int size,CacheReplacementPolicy protocol){//protocol is LFU or LRU
        capacity=size;
        cache=new HashSet<>();
        crp=protocol;
    }

    public boolean query(String word){
        return cache.contains(word);
    }
    public void add(String word){
        if(cache.size()==capacity){
            if(!query(word)){
                String toRemove=crp.remove();
                crp.remove();
                cache.remove(toRemove);
                crp.add(word);
                cache.add(word);
            }
            else{
                crp.add(word);
            }
        }
       else {
            crp.add(word);
            cache.add(word);
        }
    }
}
