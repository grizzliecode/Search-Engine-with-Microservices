package worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class WorkerController {
    private final ApplicationContext context;
    private WorkerService workerService;
    @Autowired
    public WorkerController(ApplicationContext context, WorkerService workerService1) {
        this.context = context;
        this.workerService = workerService1;
    }

    @GetMapping("/search")
    public List<FileRecord> searchFile(@RequestParam String file_name){
       return this.workerService.traverse(file_name);
    }

    @PutMapping("/stop")
    public void stop(){
        new Thread(() -> System.exit(0)).start();
    }
}
