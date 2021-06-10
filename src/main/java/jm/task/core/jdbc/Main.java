package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Денис", "Пронин", (byte) 24);
        userDaoJDBC.saveUser("Анна", "Пронина", (byte) 23);
        userDaoJDBC.saveUser("Тобайас", "Пронин", (byte) 2);
        userDaoJDBC.saveUser("ЕщеОдин", "Пронин", (byte) 0);
        List<User> userList = userDaoJDBC.getAllUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
