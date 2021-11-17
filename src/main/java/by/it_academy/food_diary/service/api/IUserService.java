package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.User;

public interface IUserService extends IService<User,Long> {
    User findByLogin(String login);

}
