package core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Cache {
    public Map<String,List<FileRecord>> mem;
    private final int MAX_SIZE = 10;
    public Cache(){
        this.mem = new LinkedHashMap<>(MAX_SIZE, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, List<FileRecord>> eldest){
                return size() > MAX_SIZE;
            }
        };
    }

    public void put(String key, List<FileRecord> results){
        this.mem.put(key,results);
    }

    public List<FileRecord> get(String key){
        return this.mem.get(key);
    }

    public boolean hit(String key){
        return this.mem.containsKey(key);
    }
}
