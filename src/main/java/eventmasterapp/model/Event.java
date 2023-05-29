package eventmasterapp.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Event(){};

    public Event(long l, String s, String s1) {
        this.id = l;
        this.name = s;
        this.description= s1;
    }
}


