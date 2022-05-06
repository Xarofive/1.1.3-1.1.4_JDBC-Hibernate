package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            NativeQuery sqlQuery = session.createSQLQuery("CREATE TABLE user (id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastName VARCHAR(20), age TINYINT UNSIGNED)");
            sqlQuery.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (PersistenceException e) {
            System.out.println("Произошла ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            NativeQuery sqlQuery = session.createSQLQuery("DROP TABLE user");
            sqlQuery.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (PersistenceException e) {
            System.out.println("Произошла ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.save(new User(name, lastName, age));
            System.out.printf("Пользователь с именем – %s добавлен в базу данных\n", name);
        } catch (PersistenceException e) {
            System.out.printf("Произошла ошибка при сохранении пользователя с именем=%s\n", name);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            User userToDelete = new User();
            userToDelete.setId(id);
            session.delete(userToDelete);
            session.getTransaction().commit();
            System.out.printf("Пользователь c id=%d удален", id);
        } catch (PersistenceException e) {
            System.out.printf("Произошла ошибка при удалении пользователя c id=%d\n", id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            List<User> users = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
            System.out.println("Получен список всех пользователей");
            return users;
        } catch (PersistenceException e) {
            System.out.println("Произошла ошибка при получении всех пользователей");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (PersistenceException e) {
            System.out.println("Произошла ошибка при очистке таблицы");
        }
    }
}
