package services.impl;

import dao.UserDAO;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import services.api.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public boolean isAlreadyExists(User user) {
        return userDAO.findByName(user.getName()) != null;
    }

    @Override
    public User authorizeUser(String name, String pass) {
        User u = userDAO.findByName(name);
        if (u != null && BCrypt.checkpw(pass, u.getPassword())) {
            return u;
        }
        return null;
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userDAO.save(user);
    }

    @Override
    public User get(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }


}
