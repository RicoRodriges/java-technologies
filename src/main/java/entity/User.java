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
    private Group group;

    public User(String name, String password, Boolean isTutor) {
        this.name = name;
        this.password = password;
        this.isTutor = isTutor;
    }

    public User(UserDto userDto) {
        id = userDto.getId();
        name = userDto.getName();
        password = userDto.getPassword();
        isTutor = userDto.getIsTutor();
    }
}
