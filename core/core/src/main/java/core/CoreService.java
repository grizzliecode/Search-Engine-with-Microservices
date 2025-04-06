package core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoreService {
    private final String configFile = ".\\config.json";
    private Cache cache;
    private List<WorkerConfiguration> workers;
    @Autowired
    public CoreService(Cache cache1){
        this.cache = cache1;
    }
    @PostConstruct
    void init(){
        try {
            File file = new File(configFile);
            ObjectMapper objectMapper = new ObjectMapper();
            this.workers = objectMapper.readValue(file, new TypeReference<List<WorkerConfiguration>>() {
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    public List<FileRecord> searchFile(String file_name){
        List<FileRecord> results = new ArrayList<>();
        if(this.cache.hit(file_name)){
            return this.cache.get(file_name);
        }
        RestTemplate restTemplate = new RestTemplate();
        for(WorkerConfiguration worker:this.workers){
            String url = String.format("http://localhost:%d/search?file_name=%s",worker.getPort(), file_name);
            try {
                System.out.println("Get request sent to:" + url);
                ResponseEntity<List<FileRecord>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FileRecord>>() {});
                List<FileRecord> resp_body = response.getBody();
                if(resp_body!=null){
                    results.addAll(resp_body);
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
        this.cache.put(file_name, results);
        return results;
    }
}
