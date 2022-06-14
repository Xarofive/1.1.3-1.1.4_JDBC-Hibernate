package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            NativeQuery sqlQuery = session.createSQLQuery("CREATE TABLE user (id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastName VARCHAR(20), age TINYINT UNSIGNED)");
            sqlQuery.executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Произошла ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            NativeQuery sqlQuery = session.createSQLQuery("DROP TABLE user");
            sqlQuery.executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Произошла ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("Пользователь с именем – %s добавлен в базу данных\n", name);
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.printf("Произошла ошибка при сохранении пользователя с именем=%s\n", name);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User userToDelete = (User) session.createQuery("FROM User u where u.id = :id", User.class);
            session.delete(userToDelete);
            transaction.commit();
            System.out.printf("Пользователь c id=%d удален", id);
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.printf("Произошла ошибка при удалении пользователя c id=%d\n", id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<User> users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            System.out.println("Получен список всех пользователей");
            return users;
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Произошла ошибка при получении всех пользователей");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Произошла ошибка при очистке таблицы");
        }
    }
}
