package entity;

import java.io.Serializable;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getTutor() {
        return isTutor;
    }

    public void setTutor(Boolean tutor) {
        isTutor = tutor;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
