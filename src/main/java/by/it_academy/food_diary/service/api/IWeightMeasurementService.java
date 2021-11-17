package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.models.WeightMeasurement;

import java.time.LocalDateTime;

public interface IWeightMeasurementService extends Iservice<WeightMeasurement,Long>{
    WeightMeasurementDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id);
}
