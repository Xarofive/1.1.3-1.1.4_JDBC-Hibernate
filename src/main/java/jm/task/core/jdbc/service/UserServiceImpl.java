package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private Connection connection;

    public UserServiceImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            System.out.println("Проблемы с соединением");
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table Users (id bigint unsigned auto_increment primary key, name varchar(20), lastName varchar(20), age tinyint unsigned)");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("drop table Users");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Таблица не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert users (name, lastName, age) values (? , ? , ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Произошла ошибка");
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from Users")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                ids.add(id);
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                userList.add((new User(name, lastName, age)));
            }

            for (int i = 0; i < userList.size(); i++) {
                userList.get(i).setId(ids.get(i));
            }

        } catch (SQLException e) {
            System.out.println("Таблица пуста или не существует");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("truncate table users");
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Таблица не существует");
        }
    }

}
