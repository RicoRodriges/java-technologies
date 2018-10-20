package entity;

import dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
