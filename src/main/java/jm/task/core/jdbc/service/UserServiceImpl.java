package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDaoJDBCImpl userDaoJDBC;

    public UserServiceImpl() {
        this.userDaoJDBC = new UserDaoJDBCImpl();
    }

    public UserServiceImpl(UserDaoJDBCImpl userDaoJDBC) {
        this.userDaoJDBC = new UserDaoJDBCImpl();
    }

    @Override
    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
        System.out.println("Таблица создана");
    }

    @Override
    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
        System.out.println("Таблица удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDaoJDBC.saveUser(name, lastName, age);
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {
        userDaoJDBC.removeUserById(id);
        System.out.println("Пользователь удален");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDaoJDBC.getAllUsers();
        System.out.println("Получен список всех пользователей");
        return users;
    }

    @Override
    public void cleanUsersTable() {
        userDaoJDBC.cleanUsersTable();
        System.out.println("Таблица очищена");
    }

}
