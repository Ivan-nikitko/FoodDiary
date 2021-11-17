package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.WeightMeasurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface IWeightMeasurementDao extends JpaRepository <WeightMeasurement,Long>{
    Page<WeightMeasurement> findAllByCreationDateBetweenAndProfileId(LocalDateTime start, LocalDateTime end, Long id, Pageable pageable);
}
