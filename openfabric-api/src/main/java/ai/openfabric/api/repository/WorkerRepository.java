package ai.openfabric.api.repository;

import ai.openfabric.api.model.Worker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, String> {

    Worker findByContainerID(String containerID);

}
