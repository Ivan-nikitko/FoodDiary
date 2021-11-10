package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.User;

import java.util.List;

public interface IUserService {
    void save(User user);
    List<User> getAll ();
    User get(Long id);
    void update(User updatedUser, Long id);
    void delete (Long id);
}
