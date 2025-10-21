package hiber.service;

import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    User findUserByCarModelAndSeries(String model, int series);
    User createUserWithCar(String firstName, String lastName, String email, String carModel, int series);
}
