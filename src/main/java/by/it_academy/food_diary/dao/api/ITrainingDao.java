package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ITrainingDao extends JpaRepository <Training,Long>{

    List<Training> findAllByCreationDateBetweenAndProfileId(LocalDateTime start, LocalDateTime end, Long id);
}
