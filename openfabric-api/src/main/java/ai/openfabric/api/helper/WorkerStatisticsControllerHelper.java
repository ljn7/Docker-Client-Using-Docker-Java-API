package ai.openfabric.api.helper;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.StatisticNetworksConfig;
import com.github.dockerjava.api.model.Statistics;

import ai.openfabric.api.controller.UpdateController;
import ai.openfabric.api.model.WorkerStatistics;
import ai.openfabric.api.repository.StatisticsRepository;

class ResultCallbackCustomImpl<T> implements ResultCallback<T> {
    public Statistics stats;

    @Override
    public void close() throws IOException {

    }

    public Statistics getStats() {
        return stats;
    }

    @Override
    public void onNext(T stats) {
        if (this.stats == null)
            this.stats = (Statistics) stats;
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onStart(Closeable closeable) {
    }
};

@Component
public class WorkerStatisticsControllerHelper {

    // public List<WorkerStatistics> getAllWorkerStats(DockerClient dockerClient) {

    // List<Container> containerList =
    // dockerClient.listContainersCmd().withShowAll(true).exec();
    // List<WorkerStatistics> result = new ArrayList<>();

    // for (Container container : containerList) {
    // result.add(getWorkerStats(container.getId(), dockerClient));
    // }

    // return result;

    // }

    public WorkerStatistics getWorkerStats(String containerId, DockerClient dockerClient,
            StatisticsRepository statisticsRepository, UpdateController updateController) {

        StatsCmd stats = dockerClient.statsCmd(containerId);

        dockerClient.statsCmd(containerId);

        ResultCallbackCustomImpl<Statistics> callback = new ResultCallbackCustomImpl<>();
        stats.exec(callback);
        while (callback.getStats() == null) {

        }
        Statistics statistics = callback.getStats();

        Long cpuUsage = statistics.getCpuStats().getCpuUsage().getTotalUsage();
        Long sysCpuUsage = statistics.getCpuStats().getSystemCpuUsage();
        Double cpuUsagePercentage = 0.0;

        if (sysCpuUsage != null && cpuUsage != null && sysCpuUsage != 0)
            cpuUsagePercentage = ((double) cpuUsage / sysCpuUsage) * 100;

        String cpuPercentage = String.format("%.2f%%", cpuUsagePercentage);
        Long mem = statistics.getMemoryStats().getUsage();
        String memUsage = ((mem != null) ? mem : 0) / (1024 * 1024) + " MiB";

        Long limit = statistics.getMemoryStats().getLimit();
        String memLimit = ((limit != null) ? limit : 0) / (1024 * 1024) + " MiB";

        String memPercentage = calculateMemoryPercentage(((mem != null) ? mem : 0), ((limit != null) ? limit : 1));

        Map<String, StatisticNetworksConfig> net = statistics.getNetworks();
        StatisticNetworksConfig netConfig = null;
        if (net != null)
            netConfig = net.get("eth0");
        Long rx = null;
        if (netConfig != null)
            rx = netConfig.getRxBytes();

        String netIO = ((rx != null) ? (rx + " B") : null);
        Long getCurrent = statistics.getPidsStats().getCurrent();
        String pid = "";
        if (getCurrent != null)
            pid = String.valueOf(getCurrent);

        return updateController.updateWorkerStatistics(
                new WorkerStatistics(containerId, cpuPercentage, memUsage, memLimit, memPercentage, netIO, pid), statisticsRepository);

    }

    private static String calculateMemoryPercentage(long usage, long limit) {
        double percentage = (double) usage / limit * 100;
        return String.format("%.2f%%", percentage);
    }
}