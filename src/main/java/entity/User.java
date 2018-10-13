package entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private long id;
    private String name;
    private String password;
    private Boolean isTutor;

    public User(String name, String password, Boolean isTutor) {
        this.name = name;
        this.password = password;
        this.isTutor = isTutor;
    }
}
