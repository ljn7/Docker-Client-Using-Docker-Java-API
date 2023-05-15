package ai.openfabric.api.model;

import lombok.Getter;
import lombok.Setter;
// import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
// import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Getter
@Setter
@Entity() /* extends Datable */
@Table(name = "worker", schema = "public")
public class Worker implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    public String id;

    @JsonProperty
    @Column(name = "containerid")
    public String containerID;

    @JsonProperty
    public String image;

    @JsonProperty
    public String command;

    @JsonProperty
    public String created;

    @JsonProperty
    public String status;

    @JsonProperty
    public String ports;

    @JsonProperty
    public String name;

    @Override
    public String toString() {
        return "Worker [containerID=" + containerID + ", image=" + image + ", command=" + command + ", created="
                + created + ", status=" + status + ", ports=" + ports + ", name=" + name + "]";
    }

    public Worker(String containerID, String image, String command, String created, String status, String ports,
            String name) {
        this.containerID = containerID;
        this.image = image;
        this.command = command;
        this.created = created;
        this.status = status;
        this.ports = ports;
        this.name = name;
    }

    public Worker() {

    }

}
