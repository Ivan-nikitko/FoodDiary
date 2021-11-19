package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository <User,Long>{
    User findByLogin(String login);
  //  Page <User> (String name, Pageable pageable);
}
