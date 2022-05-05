package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Денис", "Пронин", (byte) 24);
        userService.saveUser("Анна", "Пронина", (byte) 23);
        userService.saveUser("Тобайас", "Пронин", (byte) 2);
        userService.saveUser("ЕщеОдин", "Пронин", (byte) 0);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
