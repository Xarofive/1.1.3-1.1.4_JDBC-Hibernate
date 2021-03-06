package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при создании соединения с БД");
        }
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE user (id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age TINYINT UNSIGNED)");
            connection.commit();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE user");
            connection.commit();
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при удалении таблицы");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT user (name, lastName, age) VALUES (? , ? , ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
            System.out.printf("Пользователь с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка при сохранении пользователя с именем=%s\n", name);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
            System.out.printf("Пользователь c id=%d удален", id);
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка при удалении пользователя c id=%d\n", id);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                userList.add((new User(id, name, lastName, age)));
            }
            connection.commit();
            System.out.println("Получен список всех пользователей");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении всех пользователей");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE user");
            connection.commit();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при очистке таблицы");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
