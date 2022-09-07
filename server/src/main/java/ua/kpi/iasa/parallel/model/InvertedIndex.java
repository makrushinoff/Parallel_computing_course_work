package ua.kpi.iasa.parallel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class InvertedIndex {

    private final ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>(5000); //key - word, value - file name

    public void add(String word, String filename) {
        if (map.containsKey(word)) {
            List<String> files = map.get(word);
            files.add(filename);
            map.put(word, files);
            return;
        }
        List<String> files = new ArrayList<>();
        files.add(filename);
        map.put(word, files);
    }

    public List<String> get(String keyword) {
        return map.get(keyword);
    }

    public int getSize() {
        return map.size();
    }

}
