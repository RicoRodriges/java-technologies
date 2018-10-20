package services.impl;

import dao.UserDAO;
import dto.UserDto;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.api.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public boolean isAlreadyExists(UserDto user) {
        return userDAO.findByName(user.getName()) != null;
    }

    @Override
    public UserDto authorizeUser(String name, String pass) {
        User u = userDAO.findByName(name);
        if (u != null && BCrypt.checkpw(pass, u.getPassword())) {
            return new UserDto(u);
        }
        return null;
    }

    @Override
    public void registerUser(UserDto user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userDAO.save(new User(user));
    }

    @Override
    public UserDto get(String name) {
        User user = userDAO.findByName(name);
        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userDAO.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>(users.size());
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }


}
