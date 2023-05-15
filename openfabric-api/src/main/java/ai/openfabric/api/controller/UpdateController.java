package ai.openfabric.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.WorkerStatistics;
import ai.openfabric.api.repository.StatisticsRepository;
import ai.openfabric.api.repository.WorkerRepository;

@Component
public class UpdateController {

    public List<Worker> updateDatabase(List<Worker> entities,
            WorkerRepository repository) {

        List<Worker> res = new ArrayList<Worker>();
        repository.deleteAll();
        for (Worker entity : entities) {
            Worker worker = repository.findByContainerID(entity.getContainerID());

            if (worker == null) {
                repository.save(entity);
            } else {
                worker.setContainerID(entity.getContainerID());
                worker.setImage(entity.getImage());
                worker.setCommand(entity.getCommand());
                worker.setCreated(entity.getCreated());
                worker.setName(entity.getName());
                worker.setPorts(entity.getPorts());
                worker.setStatus(entity.getStatus());
                repository.save(worker);
            }

        }

        List<Worker> dataBaseWorkersList = new ArrayList<>();
        Iterable<Worker> entityIterable = repository.findAll();
        entityIterable.forEach(dataBaseWorkersList::add);

        Set<String> givenEntityIds = entities.stream().map(Worker::getContainerID).collect(Collectors.toSet());

        List<Worker> entitiesToDelete = dataBaseWorkersList.stream()
                .filter(entity -> !givenEntityIds.contains(entity.getContainerID()))
                .collect(Collectors.toList());
        repository.deleteAll(entitiesToDelete);

        return res;
    }

    public Worker updateStatus(String containerId, DockerClient dockerClient, WorkerRepository workerRepository) {

        List<Container> containerList = dockerClient.listContainersCmd().withShowAll(true).exec();
        Worker worker = workerRepository.findByContainerID(containerId);
        for (Container container : containerList) {

            if (container.getId().equals(containerId)) {
                worker.setStatus(container.getStatus());
                return workerRepository.save(worker);
            }
        }
        return null;
    }

    public WorkerStatistics updateWorkerStatistics(WorkerStatistics workerStatistics, StatisticsRepository repository) {

        WorkerStatistics wStatistics = repository.findByContainerID(workerStatistics.getContainerID());
        if (wStatistics == null) {
            return repository.save(workerStatistics);
        }
        wStatistics.setCpuPercentage(workerStatistics.getCpuPercentage());
        wStatistics.setMemLimit(workerStatistics.getMemLimit());
        wStatistics.setMemPercentage(workerStatistics.getMemPercentage());
        wStatistics.setMemUsage(workerStatistics.getMemUsage());
        wStatistics.setNetIO(workerStatistics.getNetIO());
        wStatistics.setPid(workerStatistics.getPid());

        return repository.save(wStatistics);
    }

}
