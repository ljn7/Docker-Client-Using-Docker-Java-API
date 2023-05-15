package ai.openfabric.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import ai.openfabric.api.config.DockerConfig;
import ai.openfabric.api.helper.WorkerStatisticsControllerHelper;
import ai.openfabric.api.model.WorkerStatistics;
import ai.openfabric.api.repository.StatisticsRepository;

@RestController
@RequestMapping("${node.api.path}")
public class WorkerStatisticsController {

    @Autowired
    private StatisticsRepository repository;
    @Autowired
    private WorkerStatisticsControllerHelper workerStatisticsControllerHelper;
    @Autowired
    private UpdateController updateController;

    private DockerClient dockerClient = DockerConfig.getInstance();

    @GetMapping("/statistics")
    public ResponseEntity<WorkerStatistics> getWorkerStatitics(@RequestParam String containerId) {

        try {
            List<Container> containerList = dockerClient.listContainersCmd().withShowAll(true).exec();
            for (Container container: containerList) {
                if (container.getId().equals(containerId)) {
                    WorkerStatistics workerStatistics = workerStatisticsControllerHelper.getWorkerStats(containerId,
                        dockerClient, repository, updateController);
                    return new ResponseEntity<>(workerStatistics, HttpStatus.OK);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
