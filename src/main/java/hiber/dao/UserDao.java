package hiber.dao;

import hiber.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);
    List<User> listUsers();

    Optional<User> findUserByCarModelAndSeries(String model, int series); // Изменено на Optional
    User createUserWithCar(String firstName, String lastName, String email, String carModel, int series);
}
