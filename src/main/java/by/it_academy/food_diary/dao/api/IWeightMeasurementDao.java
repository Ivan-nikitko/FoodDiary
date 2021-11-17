package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Training;
import by.it_academy.food_diary.models.WeightMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IWeightMeasurementDao extends JpaRepository <WeightMeasurement,Long>{
    List<WeightMeasurement> findAllByCreationDateBetweenAndProfileId(LocalDateTime start, LocalDateTime end, Long id);
}
