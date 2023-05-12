package ai.openfabric.api.repository;

import ai.openfabric.api.model.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker, String> {

}
