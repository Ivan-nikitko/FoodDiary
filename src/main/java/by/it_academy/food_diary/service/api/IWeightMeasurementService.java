package by.it_academy.food_diary.service.api;


import by.it_academy.food_diary.controller.dto.WeightMeasurementByDateDto;
import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.models.WeightMeasurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IWeightMeasurementService  {
    WeightMeasurementByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id, Pageable pageable);

    void save(WeightMeasurementDto weightMeasurementDto);

    WeightMeasurement get(Long id);

    void update(WeightMeasurementDto weightMeasurementDto, Long id);

    void delete(WeightMeasurementDto weightMeasurementDto, Long id);
}
