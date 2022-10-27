package dao;

import entity.User;

public interface UserDao {
    boolean add(User user);
    byte loginSuccess(User user);
    String getUsersObject();
    String getUser(String login);
    boolean deleteUserName(String userLogin);
}
