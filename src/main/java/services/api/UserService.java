package services.api;

import dto.UserDto;

import java.util.List;

public interface UserService {

    boolean isAlreadyExists(UserDto user);

    UserDto authorizeUser(String name, String pass);

    void registerUser(UserDto user);

    UserDto get(String name);

    List<UserDto> getAll();

}
