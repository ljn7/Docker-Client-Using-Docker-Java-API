package ai.openfabric.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import ai.openfabric.api.config.DockerConfig;
import ai.openfabric.api.controller.methods.Get;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.repository.WorkerRepository;
import io.reactivex.annotations.NonNull;

@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {

    @Autowired
    private WorkerRepository repository;
    @Autowired
    private DataUpdater dataUpdater;
    @Autowired
    private Get getMethods;

    private DockerClient dockerClient = DockerConfig.getInstance();
    // @Autowired
    // private WorkerRepository repository;

    // @PostMapping(path = "/hello")
    // public @ResponseBody String hello(@RequestBody String name) {
    //     return "Hello!" + name;
    // }
    

    @GetMapping("/workers")
    public @ResponseBody ResponseEntity<Map<String, Object>> getWorkersList(@RequestParam(required = false) Integer page) {
        
        page = page==null ? 0: page;
        try {
            List<Container> lists = dockerClient.listContainersCmd().withShowAll(true).exec();

            List<Worker> res = getMethods.getWorkersWithPagination(lists);

            dataUpdater.updateDatabase(res, repository);

            Pageable paging = PageRequest.of(page, 2);
            Page<Worker> pageWorkers;
            pageWorkers = repository.findAll(paging);
            List<Worker> contentList = pageWorkers.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("Workers", contentList);
            response.put("currentPage", pageWorkers.getNumber()+1);
            response.put("totalItems", pageWorkers.getTotalElements());
            response.put("totalPages", pageWorkers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/create/")
    public @ResponseBody ResponseEntity<Map<String, Object>> createWorker(@RequestParam String image) {
        
        if (image != null)
            dockerClient.createContainerCmd(image);
        return null;
    }

}
