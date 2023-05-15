package ai.openfabric.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Getter
@Setter
@Entity()
@Table(name = "statistics", schema = "public")
public class WorkerStatistics implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    public String id;

    @JsonProperty
    @Column(name = "containerid")
    String containerID;

    @JsonProperty
    @Column(name = "cpupercentage")
    String cpuPercentage;

    @JsonProperty
    @Column(name = "memusage")
    String memUsage;

    @JsonProperty
    @Column(name = "memlimit")
    String memLimit;

    @JsonProperty
    @Column(name = "mempercentage")
    String memPercentage;

    @JsonProperty
    @Column(name = "netio")
    String netIO;

    @JsonProperty
    String pid;

    public WorkerStatistics(String containerID, String cpuPercentage, String memUsage, String memLimit,
            String memPercentage, String netIO, String pid) {
        this.containerID = containerID;
        this.cpuPercentage = cpuPercentage;
        this.memUsage = memUsage;
        this.memLimit = memLimit;
        this.memPercentage = memPercentage;
        this.netIO = netIO;
        this.pid = pid;
    }

    public WorkerStatistics() {

    }

    @Override
    public String toString() {
        return "Worker Statistics [containerID=" + containerID + ", cpuPercentage=" + cpuPercentage + ", memUsage="
                + memUsage
                + ", memLimit=" + memLimit + ", memPercentage=" + memPercentage + ", netIO=" + netIO + ", pid=" + pid
                + "]";
    }

}