package ai.openfabric.api.helper;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Statistics;

import ai.openfabric.api.model.Worker;

@Component
public class WorkerControllerHelper {

    public List<Worker> getWorkersWithPagination(List<Container> lists) {

        List<Worker> res = new ArrayList<>();

        for (Container container : lists) {
            String containerId = container.getId();
            String image = container.getImage();
            String command = container.getCommand();
            String created = container.getCreated().toString();
            String status = container.getStatus();
            String port = ((container.getPorts().length > 0) ? (container.getPorts()[0].getIp()
                    + ":"
                    + container.getPorts()[0].getPublicPort()
                    + "->"
                    + container.getPorts()[0].getPrivatePort()
                    + "/"
                    + container.getPorts()[0].getType())
                    : "");
            String name = ((container.getNames().length > 0) ? container.getNames()[0] : "");

            Worker w = new Worker(containerId, image, command, created, status, port, name);
            w.containerID = (containerId == null) ? "1" : containerId;

            // System.out.print(temp);
            res.add(w);
            // repository.save(w);
        }
        return res;
    }
}

// class ResultCallbackImpl<T> implements ResultCallback<T> {
//     public T stats;

//     @Override
//     public void onStart(Closeable closeable) {

//     }

//     @Override
//     public void onNext(T stats) {

//         if (this.stats == null) {
//             this.stats = stats;
//         }
//     }

//     @Override
//     public void onError(Throwable throwable) {

//         throwable.printStackTrace();
//     }

//     @Override
//     public void onComplete() {

//     }

//     @Override
//     public void close() throws IOException {

//     }
// }