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


        /*
        However, the most important advantage of prepared statements is
        that they help prevent SQL injection attacks. SQL injection is
        a technique to maliciously exploit applications that use
        client-supplied data in SQL statements. Attackers trick the SQL
        engine into executing unintended commands by supplying specially
        crafted string input, thereby gaining unauthorized access to
        a database to view or manipulate restricted data. SQL injection
        techniques all exploit a single vulnerability in the application:
        Incorrectly validated or nonvalidated string literals are
        concatenated into a dynamically built SQL statement and interpreted
        as code by the SQL engine. Prepared statements always treat
        client-supplied data as content of a parameter and never as a
        part of an SQL statement. See the section SQL Injection in
        Database PL/SQL Language Reference, part of Oracle Database
        documentation, for more information.


         */


    }
}
