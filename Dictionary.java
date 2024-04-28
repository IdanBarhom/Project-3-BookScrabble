package test;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
public class Dictionary {
    private CacheManager lfu;
    private CacheManager lru;
    BloomFilter bf;
    public Dictionary(String...files){
        if(files.length>0) {
            lru = new CacheManager(400, new LRU());
            lfu = new CacheManager(100, new LFU());
            bf = new BloomFilter(256, "SHA1", "MD5");
            for(int i=0;i<files.length;i++)
                addToBloomFilter(files[i]);

        }

    }
    public void addToBloomFilter(String file)
    {
        try
        {
            Scanner reader = new Scanner(new BufferedReader(new FileReader(file)));
            String line;
            while (reader.hasNext()) // Read each line from the text
            {
                bf.add(reader.next());
            }
            reader.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    public boolean query(String word)
    {
        if(lru.query(word)) return true;
        if(lfu.query(word)) return true;
        if(bf.contains(word))
        {
            lru.add(word);
            return true;
        }
        lfu.add(word);
        return false;
    }
    public boolean challenge(String...args)
    {
        return (IOSearcher.search(args));

    }
}
