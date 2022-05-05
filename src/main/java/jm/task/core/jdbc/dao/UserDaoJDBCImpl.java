package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE user (id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age TINYINT UNSIGNED)");
        } catch (SQLException e) {
            System.out.println("Таблица уже существует или произошла другая ошибка");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE user");
        } catch (SQLException e) {
            System.out.println("Таблица не существует или произошла другая ошибка\"");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT user (name, lastName, age) VALUES (? , ? , ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка");
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id=?";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                userList.add((new User(id, name, lastName, age)));
            }
        } catch (SQLException e) {
            System.out.println("Таблица пуста или не существует");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            System.out.println("Таблица не существует или произошла другая ошибка\"");
        }
    }
}
