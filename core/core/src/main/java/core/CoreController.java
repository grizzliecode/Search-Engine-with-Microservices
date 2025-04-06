package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CoreController {
    @Autowired
    private CoreService coreService;
    @GetMapping("/search")
    public List<FileRecord> searchFile(@RequestParam String file_name){
        return coreService.searchFile(file_name);
    }
}
