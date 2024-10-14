package com.epf.Chessgame.Service;


import com.epf.Chessgame.DAO.UserDAO;
import com.epf.Chessgame.Model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public Iterable<User> getUsers() {
        return userDAO.findAll();
    }

    public User getUser(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    public void createUser(User user) {
        userDAO.save(user);
    }

    public boolean validPassword(String password){
        return password.length() >= 8;
    }
    public boolean existsByUsername(String username) {
        return userDAO.existsByUsername(username);
    }

    public User findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }
    public User updateUser(User user) {
        return userDAO.save(user);
    }

    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }
}
