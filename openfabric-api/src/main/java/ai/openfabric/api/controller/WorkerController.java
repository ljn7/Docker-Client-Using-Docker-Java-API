package ai.openfabric.api.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.ColorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import ai.openfabric.api.config.DockerConfig;
import ai.openfabric.api.repository.WorkerRepository;
import lombok.Getter;



@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {

    private DockerClient dockerClient = DockerConfig.getInstance();
    @Autowired
    private WorkerRepository repository;

    @PostMapping(path = "/hello")
    public @ResponseBody String hello(@RequestBody String name) {
        return "Hello!" + name;
    }

    @PostMapping(path = "/test")
    public @ResponseBody List<Container> testMethod(/* @RequestBody String entity */) {
        List<Container> lists = dockerClient.listContainersCmd().withShowAll(true).exec();
        
        
        for (Container container : lists) {

            System.out.print(container.getId() + "\n" 
                                + container.getImage() + "\n"
                                + container.getCommand() + "\n"
                                + container.getCreated()+ "\n"
                                + container.getStatus() + "\n"
                                 + ((container.getPorts().length > 0) ? (container.getPorts()[0].getIp() 
                                                                        + ":" 
                                                                        + container.getPorts()[0].getPublicPort()  
                                                                        + "->" 
                                                                        + container.getPorts()[0].getPrivatePort() 
                                                                        + "/" 
                                                                        + container.getPorts()[0].getType())
                                                                        : "") +  "\n"
                                + ((container.getNames().length > 0) ? container.getNames()[0]: ""));
                        
        }
        return dockerClient.listContainersCmd().withShowAll(true).exec();
    }
    
}
