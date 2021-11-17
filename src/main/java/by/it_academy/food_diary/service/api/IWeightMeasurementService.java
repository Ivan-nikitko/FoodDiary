package by.it_academy.food_diary.service.api;


import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.models.WeightMeasurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IWeightMeasurementService extends IService<WeightMeasurement,Long> {
    Page <WeightMeasurement> findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id, Pageable pageable);
}
