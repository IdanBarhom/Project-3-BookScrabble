package test;




import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {
    public Map<String,Integer> valueCounts=new HashMap<>();
    public Map<Integer,LinkedHashSet<String>> freqMap=new HashMap<>();
    public LFU() {}

    @Override
    public void add(String word){
        int currentCount = valueCounts.getOrDefault(word, 0);

        // Update valueCounts
        valueCounts.put(word, currentCount + 1);

        // Update freqMap: Remove word from its old frequency set
        freqMap.computeIfPresent(currentCount, (freq, words) -> {
            words.remove(word);
            return words.isEmpty() ? null : words; // Remove empty sets
        });

        // Add to set for new frequency
        freqMap.computeIfAbsent(currentCount + 1, k -> new LinkedHashSet<>()).add(word);
    }
    @Override
    public String remove(){
        if (freqMap.isEmpty()) {
            return null; // Or throw an exception
        }
        // Get the lowest frequency
        int minFrequency = freqMap.keySet().iterator().next();

        // Get a word from that set
        LinkedHashSet<String> wordsAtMinFreq = freqMap.get(minFrequency);
        String wordToRemove = wordsAtMinFreq.iterator().next();

        // Clean up data structures
        wordsAtMinFreq.remove(wordToRemove);
        if (wordsAtMinFreq.isEmpty()) {
            freqMap.remove(minFrequency);
        }
        valueCounts.remove(wordToRemove);

        return wordToRemove;
    }
}
