package ai.openfabric.api.model;

import lombok.Getter;
import lombok.Setter;
// import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Getter
@Setter
@Entity()
public class Worker /* extends Datable */
        implements Serializable {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    // @GenericGenerator(name = "of-uuid", strategy =
    // "ai.openfabric.api.model.IDGenerator")
    // @Getter
    // @Setter
    // public String id;
    @Id
    @JsonProperty
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

}
