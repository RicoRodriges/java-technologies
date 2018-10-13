package services.api;

import entity.User;

import java.util.List;

public interface UserService {

    boolean isAlreadyExists(User user);

    User authorizeUser(String name, String pass);

    void registerUser(User user);

    User get(long id);

    User get(String name);

    List<User> getAll();

}
