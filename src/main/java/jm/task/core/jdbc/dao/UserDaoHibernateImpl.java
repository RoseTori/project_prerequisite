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

            String createSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id)) ENGINE=InnoDB";

            session.createNativeQuery(createSQL).executeUpdate();

            transaction.commit();
            System.out.println(" Users table created successfully");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(" Error creating users table: " + e.getMessage());
            e.printStackTrace();
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

            System.out.println("DROP: Users table dropped successfully");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);

            session.save(user);

            transaction.commit();

            System.out.println("User saved successfully");
        } catch (Exception e) {

        }
    }

        @Override
        public void removeUserById ( long id){
            Transaction transaction = null;

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                User user = session.get(User.class, id);

                if (user != null) {
                    session.delete(user);
                    transaction.commit();
                }

                System.out.println("User deleted successfully");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        @Override
        public List<User> getAllUsers () {
        try  (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return  session.createQuery("from User").list();
        }
        }

        @Override
        public void cleanUsersTable () {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                String hql = "DELETE FROM User";
                Query query = session.createQuery(hql);

                int deleted = query.executeUpdate();
                transaction.commit();

                System.out.println(deleted + " users deleted successfully");
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
