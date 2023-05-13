package ai.openfabric.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import ai.openfabric.api.config.DockerConfig;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.repository.WorkerRepository;

@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {
    @Autowired
    private WorkerRepository repository;
    private DockerClient dockerClient = DockerConfig.getInstance();
    // @Autowired
    // private WorkerRepository repository;

    @PostMapping(path = "/hello")
    public @ResponseBody String hello(@RequestBody String name) {
        return "Hello!" + name;
    }

    @PostMapping(path = "/test")
    public @ResponseBody List<Worker> testMethod(/* @RequestBody String entity */) {
        List<Container> lists = dockerClient.listContainersCmd().withShowAll(true).exec();
        List<Worker> res = new ArrayList<Worker>();
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
            w.containerID = (w.getContainerID() == null) ? "1":w.getContainerID();
            System.out.println(i++ + ":" + w + '\n');

            // System.out.print(temp);
            res.add(w);
            repository.save(w);
        }
        
        // dockerClient.listContainersCmd().withShowAll(true).exec();
        // var res = repository.findAll().iterator();
        // List<Worker> t = new ArrayList<>();
        // Worker temp = new Worker(
        //             "test2",
        //             "image",
        //             "command",
        //             "created",
        //             "status",
        //             "port",
        //             "name");
        // //repository.save(temp);
        // while(res.hasNext()) {
        //     var val = res.next();
        //     t.add(val);
        //     System.out.println(val);
        // } 

        return res;
    }

}
