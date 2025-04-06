package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoreController {
    @Autowired
    private CoreService coreService;
    @GetMapping("/search")
    public List<FileRecord> searchFile(@RequestParam String file_name){
        return coreService.searchFile(file_name);
    }
}
