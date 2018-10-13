package services.impl;

import dao.UserDAO;
import entity.User;
import org.mindrot.jbcrypt.BCrypt;
import services.api.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final UserDAO userDAO = new UserDAO();

    @Override
    public boolean isAlreadyExists(User user) {
        return userDAO.get(user.getName()) != null;
    }

    @Override
    public User authorizeUser(String name, String pass) {
        User u = userDAO.get(name);
        if (u != null && BCrypt.checkpw(pass, u.getPassword())) {
            return u;
        }
        return null;
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userDAO.add(user);
    }

    @Override
    public User get(long id) {
        return userDAO.get(id);
    }

    @Override
    public User get(String name) {
        return userDAO.get(name);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }


}
