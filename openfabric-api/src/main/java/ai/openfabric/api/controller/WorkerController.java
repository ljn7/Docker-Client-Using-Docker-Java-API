package ai.openfabric.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.github.dockerjava.api.DockerClient;

import com.github.dockerjava.api.model.Container;

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
    // return "Hello!" + name;
    // }

    @GetMapping("/workers")
    public @ResponseBody ResponseEntity<Map<String, Object>> getWorkersList(
            @RequestParam(required = false) Integer page) {

        page = page == null ? 0 : page;
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
            response.put("currentPage", pageWorkers.getNumber() + ((page <= 0) ? 0 : page - 1));
            response.put("totalItems", pageWorkers.getTotalElements());
            response.put("totalPages", pageWorkers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/worker/start")
    public ResponseEntity<Worker> startWorker(@RequestParam String containerId) {

        try {
            List<Container> containerList = dockerClient.listContainersCmd().withShowAll(true).exec();
            for (Container container : containerList) {

                if (container.getId().equals(containerId)) {
                    dockerClient.startContainerCmd(containerId).exec();
                    return new ResponseEntity<>(dataUpdater.updateStatus(containerId, dockerClient, repository),
                            HttpStatus.OK);
                }

            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/worker/stop")
    public ResponseEntity<Worker> stopWorker(@RequestParam String containerId) {

        try {

            List<Container> containerList = dockerClient.listContainersCmd().withShowAll(true).exec();
            for (Container container : containerList) {

                if (container.getId().equals(containerId)) {
                    dockerClient.stopContainerCmd(containerId).exec();
                    return new ResponseEntity<>(dataUpdater.updateStatus(containerId, dockerClient, repository),
                            HttpStatus.OK);
                }

            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/worker")
    public ResponseEntity<Worker> getWorker(@RequestParam String containerId) {

        try {
            return new ResponseEntity<>(repository.findByContainerID(containerId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Worker>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
