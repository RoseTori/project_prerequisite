package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.internal.build.AllowSysOut;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
//        User user1 = new User("Ethan", "Burrows", (byte) 20);
//        User user2 = new User("Jane", "Doe", (byte) 30);
//        User user3 = new User("Bobby", "Brown", (byte) 15);
//        User user4 = new User("Mike", "White", (byte) 45);
//
//        System.out.println(user1);
//    }

        Connection conn = Util.getConnection();
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
//        userDao.saveUser("Ethan", "Burrows", (byte) 20);
//        userDao.saveUser("Jane", "Doe", (byte) 30);
//        userDao.saveUser("Bobby", "Brown", (byte) 15);
//        userDao.saveUser("Mike", "White", (byte) 45);
        userDao.getAllUsers();
//        userDao.cleanUsersTable();
//        userDao.dropUsersTable();



    }
}
