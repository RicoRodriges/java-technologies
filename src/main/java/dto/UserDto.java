package dto;

import entity.GroupEntity;
import entity.University;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private Boolean isTutor;
    private GroupEntity groupEntity;
    private University university;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.isTutor = user.getIsTutor();
        this.groupEntity = user.getGroup();
        this.university = user.getUniversity();
    }
}
