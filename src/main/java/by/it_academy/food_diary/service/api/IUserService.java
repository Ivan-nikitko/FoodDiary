package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.controller.dto.LoginDto;
import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.controller.dto.UserDto;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService  {
    User findByLogin(String login);
    void save(LoginDto loginDto);
    Page<User> getAll (Pageable pageable);


}
