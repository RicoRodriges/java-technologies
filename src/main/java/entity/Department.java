package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "faculty")
@ToString(exclude = "faculty")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "department")
    private Set<GroupEntity> groups;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Faculty faculty;
}
