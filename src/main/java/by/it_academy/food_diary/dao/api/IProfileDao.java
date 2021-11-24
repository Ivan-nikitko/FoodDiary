package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProfileDao extends JpaRepository <Profile,Long>{
    List <Profile> findAllByUserId(Long id);
}
