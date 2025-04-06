package core;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerService {
    private final String configFile = ".\\config.json";
    private final String startCommand = ".\\startWorker.ps1";
    private List<WorkerConfiguration> workers;
    
    @PreDestroy
    public void stopWorkers() {
        RestTemplate restTemplate = new RestTemplate();
        for (WorkerConfiguration worker : workers) {
            String url = "http://localhost:" + Integer.toString(worker.getPort()) + "/stop";
            System.out.println("Stopping request sent to: " + url);
            try {
                restTemplate.put(url, null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void startWorkers() {
        try {
            File file = new File(configFile);
            ObjectMapper objectMapper = new ObjectMapper();
            this.workers = objectMapper.readValue(file, new TypeReference<List<WorkerConfiguration>>() {
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
        for (WorkerConfiguration worker : workers) {
            try {
                String[] command = {"powershell.exe", "-ExecutionPolicy", "Bypass", "-File", startCommand, Integer.toString(worker.getPort()), worker.getBase_dir()};
                Process startProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
