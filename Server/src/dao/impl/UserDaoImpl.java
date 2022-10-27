package dao.impl;


import dao.UserDao;
import entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import util.ConnectionPool;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public boolean add(User user) {
        try {
            Connection connection = pool.get();
            PreparedStatement statementCheck =
                    connection.prepareStatement("SELECT * from usersinfo WHERE login = ?");
            statementCheck.setString(1, user.getLogin());
            ResultSet rs = statementCheck.executeQuery();

            if (rs.next()) {
                return false;
            } else {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO usersinfo(lastname,firstname,age,gender,login,password) VALUES (?,?,?,?,?,?)");

                statement.setString(1, user.getLastName());
                statement.setString(2, user.getName());
                statement.setInt(3, user.getAge());
                statement.setString(4, user.getGender());
                statement.setString(5, user.getLogin());
                statement.setString(6, user.getPassword());
                statement.executeUpdate();
                pool.release(connection);
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public byte loginSuccess(User user) {
        try {
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usersinfo");
            while (resultSet.next()) {
                if (user.getLogin().equals(resultSet.getString(4))) {
                    if (user.getPassword().equals(resultSet.getString(5))) {
                        if (resultSet.getString(8).equals("admin")) {
                            return (1);
                        } else return (2);
                    }
                }
            }
            pool.release(connection);
            return (3);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return (3);
        }
    }

    @Override
    public String getUsersObject() {
        try {
            ArrayList<User> usersList = new ArrayList<User>();
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usersinfo");
            while (resultSet.next()) {
                int id = resultSet.getInt("idusers");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String login = resultSet.getString("login");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                User user = new User(id, firstName, lastName, login, age, gender);
                usersList.add(user);
            }
            System.out.println(usersList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(usersList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
            return ("default");
        }


    }

    @Override
    public String getUser(String login) {
        try {

            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usersinfo");
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)) {
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    User user = new User(firstName, lastName, login, age, gender);
                    System.out.println(user);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(user);
                    pool.release(connection);
                    return json;
                }
            }

        } catch (SQLException | IOException throwables) {
            return "default";
        }
        return "default";
    }

    @Override
    public boolean deleteUserName(String userLogin) {
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM usersinfo WHERE login = ?");
            statement.setString(1, userLogin);
            statement.executeUpdate();
            pool.release(connection);
            return true;
        } catch (SQLException throwables) {

            throwables.printStackTrace();
            return false;
        }
    }
}