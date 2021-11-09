package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingDao extends JpaRepository <Training,Long>{
}
