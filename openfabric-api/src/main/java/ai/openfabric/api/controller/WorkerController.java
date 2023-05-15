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
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerConfig;
import com.github.dockerjava.api.model.ContainerHostConfig;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

import ai.openfabric.api.config.DockerConfig;
import ai.openfabric.api.helper.WorkerControllerHelper;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.repository.WorkerRepository;

@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {

    @Autowired
    private WorkerRepository repository;
    @Autowired
    private UpdateController dataUpdater;
    @Autowired
    private WorkerControllerHelper workerControllerHelper;

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

            List<Worker> res = workerControllerHelper.getWorkersWithPagination(lists);

            dataUpdater.updateDatabase(res, repository);

            Pageable paging = PageRequest.of(page, 2);
            Page<Worker> pageWorkers;
            pageWorkers = repository.findAll(paging);
            List<Worker> contentList = pageWorkers.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("Workers", contentList);
            response.put("currentPage", pageWorkers.getNumber() + ((page<=0)? 0: page-1));
            response.put("totalItems", pageWorkers.getTotalElements());
            response.put("totalPages", pageWorkers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // @GetMapping(value = "/create/")
    // public @ResponseBody ResponseEntity<Map<String, Object>> createWorker(@RequestParam String image) {
    //     String[] envs;
    //     String imageName = "nginx:latest";
    //     String containerName = "my-container";
    //     int hostPort = 8080;
    //     int containerPort = 80;

    //     // Create container configuration
    //     ContainerConfig containerConfig = new ContainerConfig();
    //     containerConfig.withImage(imageName);
        
    //     if(envs != null)
    //     containerConfig.withEnv(envs);
            
    //     // Configure port bindings
    //     PortBinding portBinding = new PortBinding("0.0.0.0", hostPort)
    //     // PortBinding.of("0.0.0.0", String.valueOf(hostPort));
    //     Ports portBindings = new Ports();
    //     portBindings.bind(portBinding, Ports.Binding.bindPort(containerPort));

    //     // Configure host configuration
    //     HostConfig hostConfig = HostConfig.builder()
    //             .portBindings(portBindings)
    //             .build();

    //     // Create the container
    //     CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        
    //     if (containerName != null)
    //         containerCmd = containerCmd.withName(containerName);
    //     if (hostConfig != null)
    //         containerCmd = containerCmd.withHostConfig(hostConfig);

    //     ContainerRe containerResponse = containerCmd.exec();
    //     // Get the ID of the created container
    //     String containerId = containerResponse.getId();
    //     System.out.println("Container created with ID: " + containerId);
    // }
    
    
}
