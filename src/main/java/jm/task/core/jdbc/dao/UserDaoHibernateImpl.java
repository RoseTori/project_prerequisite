package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id)) ENGINE=InnoDB";

            session.createNativeQuery(createTableSQL).executeUpdate();

            transaction.commit();
            System.out.println("Users table has been created");

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String dropTableSql = "DROP TABLE IF EXISTS users";
            Query query = session.createNativeQuery(dropTableSql);
            query.executeUpdate();

            transaction.commit();

            System.out.println("Users table has been dropped");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Long userId = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
            userId = user.getId();
            System.out.println("User '" + name + " " + lastName + "' saved with ID: " + userId);
        } catch (Exception e) {
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createQuery("delete from User where id=:id");
            query.setLong("id", id);

            int result = query.executeUpdate();

            if (result > 0) {
                System.out.println("User with ID: " + id + " has been removed");
            } else {
                System.out.println("User with ID: " + id + " has NOT been removed");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = "FROM User";
            Query<User> query = session.createQuery(queryString, User.class);

            List<User> users = query.getResultList();

            System.out.println("Retrieved " +  users.size() + " users");
            return users;
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM User");
            int result = query.executeUpdate();

            transaction.commit();

            System.out.println("Cleaned " +  result + " users");
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
