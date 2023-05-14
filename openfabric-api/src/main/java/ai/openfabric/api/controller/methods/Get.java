package ai.openfabric.api.controller.methods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.model.Container;

import ai.openfabric.api.model.Worker;

@Component
public class Get {

    public List<Worker> getWorkersWithPagination(List<Container> lists) {

        List<Worker> res = new ArrayList<>();
        int i = 0;
        for (Container container : lists) {
            String id = container.getId();
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

            Worker w = new Worker(id, image, command, created, status, port, name);
            w.containerID = (w.getContainerID() == null) ? "1" : w.getContainerID();

            // System.out.print(temp);
            res.add(w);
            // repository.save(w);
        }
        return res;
    }
    
    // public Worker createContainer () {
        
    // }

}
