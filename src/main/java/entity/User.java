package entity;

import dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private Boolean isTutor;
    @ManyToOne
    @JoinColumn
    private GroupEntity group;
    @ManyToOne
    @JoinColumn
    private University university;

    public User(String name, String password, Boolean isTutor, GroupEntity group, University university) {
        this.name = name;
        this.password = password;
        this.isTutor = isTutor;
        this.group = group;
        this.university = university;
    }

    public User(UserDto userDto) {
        id = userDto.getId();
        name = userDto.getName();
        password = userDto.getPassword();
        isTutor = userDto.getIsTutor();
        group = userDto.getGroupEntity();
        university = userDto.getUniversity();
    }
}
