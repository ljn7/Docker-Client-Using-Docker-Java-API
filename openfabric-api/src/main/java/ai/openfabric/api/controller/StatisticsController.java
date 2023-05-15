package ai.openfabric.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${node.api.path}/statistics")
public class StatisticsController {
    
    @GetMapping("/test")
    public String test () {
        
        

        return "";
    }

}
