package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import sun.jvm.hotspot.debugger.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Subgraph;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.createUserWithCar("user1", "Miller","user@1", "Toyota", 1);

       List<User> users = userService.listUsers();
       for (User user : users) {
           System.out.println("Id = " + user.getId());
           System.out.println("First Name = " + user.getFirstName());
           System.out.println("Last Name = " + user.getLastName());
           System.out.println("Email = " + user.getEmail());
           if (user.getCar() != null) {
               System.out.println("Car = " + user.getCar().getModel() + " Series " + user.getCar().getSeries());
           }
           System.out.println();
       }
       User found = userService.findUserByCarModelAndSeries("Toyota", 1);

       if (found != null) {
           System.out.println("User found:");
           System.out.println(" ID : " + found.getId());
           System.out.println(" Name" + found.getFirstName() + " " + found.getLastName());
           System.out.println(" Email: " + found.getEmail());
       }

      context.close();



   }
}
