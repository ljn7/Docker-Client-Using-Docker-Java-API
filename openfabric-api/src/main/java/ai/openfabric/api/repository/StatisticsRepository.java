package ai.openfabric.api.repository;

import ai.openfabric.api.model.Statistics;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {

}
