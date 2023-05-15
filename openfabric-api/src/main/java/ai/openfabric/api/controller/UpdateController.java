package ai.openfabric.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ai.openfabric.api.model.Worker;
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
        // get list from database and compare with updated entities,
        // if not found in updated list then container record is removed
        List<Worker> dataBaseWorkersList = new ArrayList<>();
        Iterable<Worker> entityIterable = repository.findAll();
        entityIterable.forEach(dataBaseWorkersList::add);

        Set<String> givenEntityIds = entities.stream().map(Worker::getContainerID).collect(Collectors.toSet());
        // Set<String> ss =
        // dataBaseWorkersList.stream().map(Worker::getContainerID).filter(e ->
        // true).collect(Collectors.toSet());
        List<Worker> entitiesToDelete = dataBaseWorkersList.stream()
                .filter(entity -> !givenEntityIds.contains(entity.getContainerID()))
                .collect(Collectors.toList());
        repository.deleteAll(entitiesToDelete);

        return res;
    }

}
